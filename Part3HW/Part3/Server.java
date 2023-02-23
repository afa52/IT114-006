package Part3HW.Part3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Server {
    int port = 3001;

    // connected clients
    private List<ServerThread> clients = new ArrayList<ServerThread>();

    private void start(int port) {
        this.port = port;
        // server listening
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            Socket incoming_client = null;
            System.out.println("Server is listening on port " + port);
            do {
                System.out.println("waiting for next client");
                if (incoming_client != null) {
                    System.out.println("Client connected");
                    ServerThread sClient = new ServerThread(incoming_client, this);

                    clients.add(sClient);
                    sClient.start();
                    incoming_client = null;

                }
            } while ((incoming_client = serverSocket.accept()) != null);
        } catch (IOException e) {
            System.err.println("Error accepting connection");
            e.printStackTrace();
        } finally {
            System.out.println("closing server socket");
        }
    }

    protected synchronized void disconnect(ServerThread client) {
        long id = client.getId();
        client.disconnect();
        broadcast("Disconnected", id);
    }

    protected synchronized void broadcast(String message, long id) {
        if (processCommand(message, id)) {

            return;
        }
        // let's temporarily use the thread id as the client identifier to
        // show in all client's chat. This isn't good practice since it's subject to
        // change as clients connect/disconnect
        message = String.format("User[%d]: %s", id, message);
        // end temp identifier

        // loop over clients and send out the message
        Iterator<ServerThread> it = clients.iterator();
        while (it.hasNext()) {
            ServerThread client = it.next();
            boolean wasSuccessful = client.send(message);
            if (!wasSuccessful) {
                System.out.println(String.format("Removing disconnected client[%s] from list", client.getId()));
                it.remove();
                broadcast("Disconnected", id);
            }
        }
    }

    private boolean processCommand(String message, long clientId) {
        processCoinTossCommand(message, clientId);
        processDiceRollCommand(message, clientId);
        System.out.println("Checking command: " + message);
        if (message.matches("smack")) { // format #d#
            String finalMessage = String.format("User[%d] can go smack himsel", clientId);
            broadcast(finalMessage, clientId);
        }
        if (message.equalsIgnoreCase("disconnect")) {
            Iterator<ServerThread> it = clients.iterator();
            while (it.hasNext()) {
                ServerThread client = it.next();
                if (client.getId() == clientId) {
                    it.remove();
                    disconnect(client);

                    break;
                }
            }
            return true;
        }
        return false;
    }

    /*
     * Implementation 1
     * afa52
     * IT114-006
     * 2-23-23
     * The method processDiceRollCommand() takes two parameters and inside the
     * method the
     * will check if the user inputted message matches the specific format
     * "roll #d#" using
     * "roll\\s\\d+d\\d+". If the message matches the the format, it will take the
     * string input
     * and split it into a two element array ({"roll","#d#"}). Then, split [1]
     * element in parts[]
     * array ({"#d#"}) before and after "d", assigning numDice to the first number
     * and numSides to
     * second number. Then the method creates a Random object to simulate a dice
     * roll. With a for
     * loop, the method will roll the specified numDice with the specified numSides,
     * and it will
     * add the results of each roll in an int variable "total". Finally, the method
     * creates a string
     * using String.format() that includes user's ID, numDice, numSides, and total
     * and then broadcasts
     * the message to all connected clients.
     * 
     */
    private void processDiceRollCommand(String message, long clientID) {
        if (message.matches("roll\\s\\d+d\\d+")) { // format #d#
            String[] parts = message.split("\\s"); // split after white space
            int numDice = Integer.parseInt(parts[1].split("d")[0]);
            int numSides = Integer.parseInt(parts[1].split("d")[1]);
            Random r = new Random();
            int total = 0;
            for (int i = 0; i < numDice; i++) {
                int roll = r.nextInt(numSides) + 1;
                total += roll;
            }
            String finalMessage = String.format("User[%d] rolled %dd%d and %d", clientID, numDice, numSides, total);
            broadcast(finalMessage, clientID);
        }
    }

    /*
     * Implementation 2
     * afa52
     * IT114-006
     * 2-23-23
     * The method processCoinTossComannd() is similar to above method where it takes
     * in the same
     * parameters and then checks if the message input matches a paticular word. If
     * true, the method
     * creates a random object that will provide a random number between 0-1 and
     * assign that number to
     * a int variable called 'index'. Using an if condition, if index is equal to 0,
     * assign "Tails" to
     * the empty string variable headTail. Else, assign the empty string variable to
     * "Heads". Finally,
     * the method will create a string using String.format() that includes clientID
     * and headTail and
     * then broadcasts the message to all connected clients.
     * 
     */
    private void processCoinTossCommand(String message, long clientID) {
        if (message.matches("coin|toss|flip")) {
            Random r = new Random();
            int index = r.nextInt(2);
            String headTail;
            if (index == 0) {
                headTail = "Tails";
            } else {
                headTail = "Heads";
            }
            String resultMessage = String.format("User[%d] flipped a coin and got %s", clientID, headTail);
            broadcast(resultMessage, clientID);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting Server");
        Server server = new Server();
        int port = 3000;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            // can ignore, will either be index out of bounds or type mismatch
            // will default to the defined value prior to the try/catch
        }
        server.start(port);
        System.out.println("Server Stopped");
    }
}