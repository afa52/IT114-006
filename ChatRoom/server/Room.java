package ChatRoom.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import ChatRoom.common.Constants;

public class Room implements AutoCloseable {
    protected static Server server;// used to refer to accessible server functions
    private String name;
    private List<ServerThread> clients = new ArrayList<ServerThread>();
    private boolean isRunning = false;
    // Commands
    private final static String COMMAND_TRIGGER = "/";
    private final static String CREATE_ROOM = "createroom";
    private final static String JOIN_ROOM = "joinroom";
    private final static String DISCONNECT = "disconnect";
    private final static String FLIP = "flip"; // flip command
    private final static String ROLL = "roll"; // roll command
    private final static String LOGOUT = "logout";
    private final static String LOGOFF = "logoff";
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
            sendConnectionStatus(client, true);
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
            sendConnectionStatus(client, false);
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

    /***
     * Helper function to process messages to trigger different functionality.
     * 
     * @param message The original message being sent
     * @param client  The sender of the message (since they'll be the ones
     *                triggering the actions)
     */
    @Deprecated // not used in my project as of this lesson, keeping it here in case things
                // change
    private boolean processCommands(String message, ServerThread client) {
        boolean wasCommand = false;
        String result = null;
        try {
            if (message.startsWith(COMMAND_TRIGGER)) {
                String[] comm = message.split(COMMAND_TRIGGER);
                String part1 = comm[1];
                String[] comm2 = part1.split(" ");
                String command = comm2[0];
                String roomName;
                wasCommand = true;
                switch (command) {
                    case CREATE_ROOM:
                        roomName = comm2[1];
                        Room.createRoom(roomName, client);
                        break;
                    case JOIN_ROOM:
                        roomName = comm2[1];
                        Room.joinRoom(roomName, client);
                        break;
                    /*  afa52                                                                                              04-03-2023
                    *   case ROLL: waits for /roll command from user. Creates a string array rollArgs that splits the message at any 
                    *   whitespace characters. The condition statement checks if rollArgs == 2, meaning that the user entered two words, 
                    *   AND the second word in the array matches the format ("#d#"), then the code block executes. It will first split 
                    *   the second word of rollArgs at the letter "d", and then assign the variable numDice to the number left of 'd' a
                    *   "(dice[0])" and assign the variable max to the number right of 'd' as "(dice[1])". An int variable total is also 
                    *   assigned to 0, and later used in the--
                    *   for loop that rolls the assigned number of dice with the assigned max value. Finally, the variable result is set
                    *   to how many dice the user rolled and what the total value was. The else-if code block tests if the user entered
                    *   two words but without the format. If executed, then it will take the second word of rollArgs[1] and change it to
                    *   an int value using parseInt and assign to the variable max. Another int variable num is random generated number 
                    *   between 1 and the user inputted integer. If the user calls the command but does not match the format or does not 
                    *   input an integer, then the result variable is assigned to an error message. Lastly, the sendMessage() function is
                    *   called to send the results back to the user. */ 
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
                            result = "You rolled " + numDice + "d" + max + " and got " + total;
                        } else if (rollArgs.length == 2) {
                            try {
                                int max = Integer.parseInt(rollArgs[1]);
                                int num = (int) (Math.random() * max) + 1;
                                result = "You got a " + num;
                            } catch (NumberFormatException e) {
                                result = "Invalid command format.";
                            }
                        } else {
                            result = "Invalid command format.";
                        }
                        sendMessage(client, result);
                        break;
                    /*  afa52                                                                                              04-03-2023
                    *   case FLIP: waits for /flip command from user. The first line stores a random number between 0 and 1 into a 
                    *   variable called flip. The condition statement checks if flip is less than 0.5, if true the result is "heads" and
                    *   else the result is "tails". Finally, the sendMessage() function is called to send the results back to the user.*/ 
                    case FLIP:
                        double flip = Math.random();
                        if (flip < 0.5) {
                            result = "Heads";
                        } else {
                            result = "Tails";
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
        if (server.createNewRoom(roomName)) {
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
        if (!server.joinRoom(roomName, client)) {
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
        if (!isRunning) {
            return;
        }
        logger.info(String.format("Sending message to %s clients", clients.size()));
        if (sender != null && processCommands(message, sender)) {
            // it was a command, don't broadcast
            return;
        }
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

    protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
        Iterator<ServerThread> iter = clients.iterator();
        while (iter.hasNext()) {
            ServerThread receivingClient = iter.next();
            boolean messageSent = receivingClient.sendConnectionStatus(
                    sender.getClientId(),
                    sender.getClientName(),
                    isConnected);
            if (!messageSent) {
                handleDisconnect(iter, receivingClient);
            }
        }
    }

    private void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
        iter.remove();
        logger.info(String.format("Removed client %s", client.getClientName()));
        sendMessage(null, client.getClientName() + " disconnected");
        checkClients();
    }

    public void close() {
        server.removeRoom(this);
        isRunning = false;
        clients.clear();
    }

}