package CR.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import CR.common.ClientPayload;
import CR.common.Constants;
import CR.common.MyLogger;
import CR.common.Payload;
import CR.common.PayloadType;
import CR.common.RoomResultPayload;

/**
 * A server-side representation of a single client
 */
public class ServerThread extends Thread {
    private Socket client;
    private String clientName;
    private String formattedName;
    private boolean isRunning = false;
    private ObjectOutputStream out;// exposed here for send()
    // private Server server;// ref to our server so we can call methods on it
    // more easily
    private Room currentRoom;
    // private static Logger logger =
    // Logger.getLogger(ServerThread.class.getName());
    private static MyLogger logger = MyLogger.getLogger(ServerThread.class.getName());
    private long myId;
    List<String> mutedClients = new ArrayList<String>();

    public List<String> getMutedClients() {
        return this.mutedClients;
    }

    public void mute(String name) {
        System.out.println(String.format("Adding [%s] to your mute list", getId(), name));
        name = name.trim().toLowerCase();
        if (!isMuted(name)) {
            mutedClients.add(name);
            saveMuteList();
            syncIsMuted(name, true);

            // Create and send a payload indicating that the client has been muted
            Payload p = new Payload();
            p.setPayloadType(PayloadType.MUTE_LIST);
            p.setClientName(name);
            p.setFlag(true);
            send(p);
        }
    }

    public void unmute(String name) {
        name = name.trim().toLowerCase();
        if (isMuted(name)) {
            System.out.println("OK..");
            mutedClients.remove(name);
            System.out.println("Unmuting client " + name);
            saveMuteList();
            syncIsMuted(name, false);
        }
    }

    // checks to see if client is muted
    public boolean isMuted(String name) {
        name = name.trim().toLowerCase();
        return mutedClients.contains(name);
    }

    // overwrites client's mutedClients list to a file
    void saveMuteList() {
        String data = clientName + ": " + String.join(", ", mutedClients);
        try {
            FileWriter export = new FileWriter(clientName + ".txt");
            BufferedWriter bw = new BufferedWriter(export);
            bw.write("" + data); // convert StringBuilder to string
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // loads client's mutedClients list on reconnect
    void loadMuteList() {
        File file = new File(clientName + ".txt");
        if (file.exists()) {
            try (Scanner reader = new Scanner(file)) {
                String dataFromFile = "";
                while (reader.hasNextLine()) {
                    String text = reader.nextLine();
                    dataFromFile += text;
                }
                dataFromFile = dataFromFile.substring(dataFromFile.indexOf(" ") + 1);
                ;
                if (!dataFromFile.strip().equals("") && !dataFromFile.isEmpty()) {
                    List<String> getClients = Arrays.asList(dataFromFile.split(", "));
                    for (String client : getClients) {
                        mute(client);
                        System.out.println("sync");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        System.out.println(mutedClients.toString());
    }

    public void setClientId(long id) {
        myId = id;
    }

    public long getClientId() {
        return myId;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void info(String message) {
        System.out.println(String.format("Thread[%s]: %s", getId(), message));
    }

    public ServerThread(Socket myClient, Room room) {
        info("Thread created");
        // get communication channels to single client
        this.client = myClient;
        this.currentRoom = room;
        this.mutedClients = new ArrayList<String>();

    }

    public void setFormattedName(String name) {
        formattedName = name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    protected void setClientName(String name) {
        if (name == null || name.isBlank()) {
            System.err.println("Invalid client name being set");
            return;
        }
        clientName = name;
    }

    public String getClientName() {
        return clientName;
    }

    protected synchronized Room getCurrentRoom() {
        return currentRoom;
    }

    protected synchronized void setCurrentRoom(Room room) {
        if (room != null) {
            currentRoom = room;
        } else {
            info("Passed in room was null, this shouldn't happen");
        }
    }

    public void disconnect() {
        sendConnectionStatus(myId, getClientName(), null, false);
        info("Thread being disconnected by server");
        isRunning = false;
        cleanup();
    }

    public boolean sendRoomName(String name) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.JOIN_ROOM);
        p.setMessage(name);
        return send(p);
    }

    public boolean sendRoomsList(String[] rooms, String message) {
        RoomResultPayload payload = new RoomResultPayload();
        payload.setRooms(rooms);
        // Fixed in Module7.Part9
        if (message != null) {
            payload.setMessage(message);
        }
        return send(payload);
    }

    public boolean sendExistingClient(long clientId, String clientName, String formattedName) {
        ClientPayload p = new ClientPayload();
        p.setPayloadType(PayloadType.SYNC_CLIENT);
        p.setClientId(clientId);
        p.setFormattedName(formattedName);
        p.setClientName(clientName);
        return send(p);
    }

    public boolean sendResetUserList() {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.RESET_USER_LIST);
        return send(p);
    }

    public boolean sendClientId(long id) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CLIENT_ID);
        p.setClientId(id);
        return send(p);
    }

    public boolean sendMessage(long clientId, String message) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MESSAGE);
        p.setClientId(clientId);
        p.setMessage(message);
        return send(p);
    }

    public boolean sendConnectionStatus(long clientId, String who, String formattedName, boolean isConnected) {
        ClientPayload p = new ClientPayload();
        p.setPayloadType(isConnected ? PayloadType.CONNECT : PayloadType.DISCONNECT);
        p.setClientId(clientId);
        p.setFormattedName(formattedName);
        p.setClientName(who);
        p.setMessage(isConnected ? "connected" : "disconnected");
        return send(p);
    }

    private synchronized boolean send(Payload payload) {
        // added a boolean so we can see if the send was successful
        try {
            // TODO add logger
            synchronized (out) {
                logger.fine("Outgoing payload: " + payload);
                out.writeObject(payload);
                logger.fine("Sent payload: " + payload);
            }
            return true;
        } catch (IOException e) {
            info("Error sending message to client (most likely disconnected)");
            // comment this out to inspect the stack trace
            // e.printStackTrace();
            cleanup();
            return false;
        } catch (NullPointerException ne) {
            info("Message was attempted to be sent before outbound stream was opened: " + payload);
            return true;// true since it's likely pending being opened
        }
    }

    // end send methods
    @Override
    public void run() {
        info("Thread starting");
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
            this.out = out;
            isRunning = true;
            Payload fromClient = new Payload();
            synchronized (in) {
                while (isRunning && // flag to let us easily control the loop
                        (fromClient = (Payload) in.readObject()) != null // reads an object from inputStream (null would
                                                                         // likely mean a disconnect)
                ) {

                    logger.fine("Received from client: " + fromClient);
                    processPayload(fromClient);

                }
            }
        } catch (Exception e) {
            // happens when client disconnects
            e.printStackTrace();
            info("Client disconnected");
        } finally {
            isRunning = false;
            info("Exited thread loop. Cleaning up connection");
            cleanup();
        }
    }

    // sends client mute or unmute to clientside through payload
    protected boolean syncIsMuted(String clientName, boolean isMuted) { // afa52
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MUTE_LIST);
        p.setClientName(clientName);
        p.setFlag(isMuted);
        return send(p);
    }

    void processPayload(Payload p) {
        switch (p.getPayloadType()) {
            case CONNECT:
                setClientName(p.getClientName());
                break;
            case DISCONNECT:
                Room.disconnectClient(this, getCurrentRoom());
                break;
            case MESSAGE:
                if (currentRoom != null) {
                    currentRoom.sendMessage(this, p.getMessage());
                } else {
                    // TODO migrate to lobby
                    logger.info("Migrating to lobby on message with null room");
                    Room.joinRoom(Constants.LOBBY, this);
                }
                break;
            case GET_ROOMS:
                Room.getRooms(p.getMessage().trim(), this);
                break;
            case CREATE_ROOM:
                Room.createRoom(p.getMessage().trim(), this);
                break;
            case JOIN_ROOM:
                Room.joinRoom(p.getMessage().trim(), this);
                break;
            default:
                logger.warning(String.format("Unrecognized payload type: %s", p.getPayloadType()));
                break;

        }

    }

    private void cleanup() {
        info("Thread cleanup() start");
        try {
            client.close();
        } catch (IOException e) {
            info("Client already closed");
        }
        info("Thread cleanup() complete");
    }

}