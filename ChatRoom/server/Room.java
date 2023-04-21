package ChatRoom.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ChatRoom.common.Constants;

public class Room implements AutoCloseable {
    private String name;
    protected List<ServerThread> clients = new ArrayList<ServerThread>();
    private boolean isRunning = false;

    // Commands
    private final static String COMMAND_TRIGGER = "/";
    private final static String CREATE_ROOM = "createroom";
    private final static String JOIN_ROOM = "joinroom";
    private final static String DISCONNECT = "disconnect";
    private final static String LOGOUT = "logout";
    private final static String LOGOFF = "logoff";
    private final static String ROLL = "roll";
    private final static String FLIP = "flip";
    private final static String WHISPER = "@";

    private static Logger logger = Logger.getLogger(Room.class.getName());

    public Room(String name) {
        this.name = name;
        isRunning = true;
    }

    public String getName() {
        return name;
    }

    public boolean isRunning() {
        return isRunning;
    }

    protected synchronized void addClient(ServerThread client) {
        logger.info("Room addClient called");
        if (!isRunning) {
            return;
        }
        client.setCurrentRoom(this);
        if (clients.indexOf(client) > -1) {
            logger.warning("Attempting to add client that already exists in room");
        } else {
            clients.add(client);
            client.sendResetUserList();
            syncCurrentUsers(client);
            sendConnectionStatus(client, true, "joined the room" + getName());
        }
    }

    protected synchronized void removeClient(ServerThread client) {
        if (!isRunning) {
            return;
        }
        // attempt to remove client from room
        try {
            clients.remove(client);
        } catch (Exception e) {
            logger.severe(String.format("Error removing client from room %s", e.getMessage()));
            e.printStackTrace();
        }
        // if there are still clients tell them this person left
        if (clients.size() > 0) {
            sendConnectionStatus(client, false, "left the room" + getName());
        }
        checkClients();
    }

    private void syncCurrentUsers(ServerThread client) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread existingClient = iter.next();
            if (existingClient.getClientId() == client.getClientId()) {
                continue;// don't sync ourselves
            }
            boolean messageSent = client.sendExistingClient(existingClient.getClientId(),
                    existingClient.getClientName());
            if (!messageSent) {
                handleDisconnect(iter, existingClient);
                break;// since it's only 1 client receiving all the data, break if any 1 send fails
            }
        }
    }

    /***
     * Checks the number of clients.
     * If zero, begins the cleanup process to dispose of the room
     */
    private void checkClients() {
        // Cleanup if room is empty and not lobby
        if (!name.equalsIgnoreCase(Constants.LOBBY) && (clients == null || clients.size() == 0)) {
            close();
        }
    }

    private boolean processCommands(String message, ServerThread client) {
        boolean wasCommand = false;
        try {
            if (message.startsWith(COMMAND_TRIGGER)) {
                String[] comm = message.split(COMMAND_TRIGGER);

                logger.log(Level.INFO, message);
                String part1 = comm[1];
                String[] comm2 = part1.split(" ");
                String command = comm2[0];
                String roomName;
                wasCommand = true;
                String result;
                switch (command) {
                    case CREATE_ROOM:
                        roomName = comm2[1];
                        Room.createRoom(roomName, client);
                        break;
                    case JOIN_ROOM:
                        roomName = comm2[1];
                        Room.joinRoom(roomName, client);
                        break;
                    case WHISPER:
                        String clientName = comm2[1];
                        clientName = clientName.trim().toLowerCase();
                        List<String> clients = new ArrayList<String>();
                        clients.add(clientName);
                        //sendPrivateMessage(client, clients, message);
                        break;
                    case ROLL:
                        String[] rollArgs = message.split("\\s+"); 
                        if (rollArgs.length == 2 && rollArgs[1].matches("\\d+d\\d+")) {
                            String[] dice = rollArgs[1].split("d");
                            int numDice = Integer.parseInt(dice[0]);
                            int max = Integer.parseInt(dice[1]);
                            int total = 0;
                            for (int i = 0; i < numDice; i++) {
                                total += (int) (Math.random() * max) + 1;
                            }
                            sendMessage(client, "<b><i><font color=green> rolled " + numDice + "d" + max + " and got " + total + "</font></i></b>");
                        } else if (rollArgs.length == 2) {
                            try {
                                int max = Integer.parseInt(rollArgs[1]);
                                int num = (int) (Math.random() * max) + 1;
                            sendMessage(client, "<b><i><font color=purple> rolled a " + num + "</font></i></b>");
                            } catch (NumberFormatException e) {
                                sendMessage(client, "<i><font color=red> Invalid command format.</font></i>");
                            }
                        } else {
                            sendMessage(client, "<i><font color=red> Invalid command format.</font></i>");
                        }
                        break;              
                    case FLIP:
                        double flip = Math.random();
                        if (flip < 0.5) {
                            result = "<b style=color:red><i>TAILS</i></b>";
                        } else {
                            result = "<b style=color:blue><i>HEADS</i></b>";
                        }
                        sendMessage(client, result);
                        break;
                    case DISCONNECT:
                    case LOGOUT:
                    case LOGOFF:
                        Room.disconnectClient(client, this);
                        break;
                    default:
                        wasCommand = false;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wasCommand;
    }

    // Command helper methods
    protected static void getRooms(String query, ServerThread client) {
        String[] rooms = Server.INSTANCE.getRooms(query).toArray(new String[0]);
        client.sendRoomsList(rooms,
                (rooms != null && rooms.length == 0) ? "No rooms found containing your query string" : null);
    }

    protected static void createRoom(String roomName, ServerThread client) {
        if (Server.INSTANCE.createNewRoom(roomName)) {
            Room.joinRoom(roomName, client);
        } else {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s already exists", roomName));
        }
    }

    /**
     * Will cause the client to leave the current room and be moved to the new room
     * if applicable
     * 
     * @param roomName
     * @param client
     */
    protected static void joinRoom(String roomName, ServerThread client) {
        if (!Server.INSTANCE.joinRoom(roomName, client)) {
            client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s doesn't exist", roomName));
        }
    }

    protected static void disconnectClient(ServerThread client, Room room) {
        client.disconnect();
        room.removeClient(client);
    }
    // end command helper methods

    /***
     * Takes a sender and a message and broadcasts the message to all clients in
     * this room. Client is mostly passed for command purposes but we can also use
     * it to extract other client info.
     * 
     * @param sender  The client sending the message
     * @param message The message to broadcast inside the room
     */
    protected synchronized void sendMessage(ServerThread sender, String message) {
        logger.log(Level.INFO, getName() + ": Sending message to " + clients.size() + "clients");
        
        if (!isRunning) {
            return;
        }
        logger.info(String.format("Sending message to %s clients", clients.size()));
        if (sender != null && processCommands(message, sender)) {
            // it was a command, don't broadcast
            return;
        }
        //is private message
        //filter message
        long from = sender == null ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread client = iter.next();
            boolean messageSent = client.sendMessage(from, message);
            if (!messageSent) {
                handleDisconnect(iter, client);
            }
        }
    }
    protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected, String message) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread receivingClient = iter.next();
            boolean messageSent = receivingClient.sendConnectionStatus(
                    sender.getClientId(),
                    sender.getClientName(),
                    isConnected);
            if (!messageSent) {
                handleDisconnect(iter, receivingClient);
                logger.info("Removed client" + receivingClient.getClientId() );
            }
        }
    }

    protected void sendPrivateMessage(ServerThread sender, String message, List<String> users) {
        logger.log(Level.INFO, getName() + ": Sending message to " + users.size() + " clients");
        if (processCommands(message, sender)) {
            // it was a command, don't broadcast
            return;
        }
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread client = iter.next();
                // send message if sender not muted
            if(users.contains(client.getClientName().toLowerCase())) {
                if (!client.isMuted(sender.getClientName())){
                    boolean messageSent = client.sendMessage(client.getClientId(), message);
                    if (!messageSent) {
                        iter.remove();
                    }
                }
            }
        }
        }

    protected void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
        if (iter != null) {
            iter.remove();
        } else {
            Iterator<ServerThread> iter2 = clients.iterator();
            while (iter2.hasNext()) {
                ServerThread th = iter2.next();
                if (th.getClientId() == client.getClientId()) {
                    iter2.remove();
                    break;
                }
            }
        }
        logger.log(Level.INFO, "Removed client %s", client.getClientName());
        sendMessage(null, client.getClientName() + " disconnected");
        checkClients();
    }

    public void close() {
        Server.INSTANCE.removeRoom(this);
        isRunning = false;
        clients.clear();
    }

}