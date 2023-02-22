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
    private boolean isGameActive;
    private int hiddenNum;
    private String clientID;
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
        if(processCommand(message, id)){

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

    private boolean processCommand(String message, long clientId){
        processGuess(message, clientId);
        processCoinTossCommand(message, clientId);
        System.out.println("Checking command: " + message);
        if(message.equalsIgnoreCase("disconnect")){
            Iterator<ServerThread> it = clients.iterator();
            while (it.hasNext()) {
                ServerThread client = it.next();
                if(client.getId() == clientId){
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
        Implementation 1 
        afa52
        IT114-006
        2-21-23

    */
    private void processCoinTossCommand(String message, long clientID) {
        if(message.matches("coin|toss|flip")){
            Random r = new Random();
            int index = r.nextInt(2);
            String headTail;
            if(index == 0) {
                headTail = "Tails";
            }
            else {
                headTail = "Heads";
            }
            String resultMessage = String.format("User[%d] flipped a coin and got %s", clientID, headTail);
            broadcast(resultMessage, clientID);
        }
    }
    /*
        Implementation 2
        afa52
        IT114-006
        2-21-23

    */
    private void startGame(long clientID) {
        isGameActive = true;
        hiddenNum = new Random().nextInt(10) + 1;
        String startMessage = String.format("User %s has started a number guessing game. Guess a number from 1 to 10. ", clientID);
        broadcast(startMessage, -1);
    }
    private void endGame(long clientID) {
        isGameActive = false;
        hiddenNum = 0;
        String endMessage = String.format("The game has ended.", clientID);
        broadcast(endMessage, -1);
    }
    private void processGuess(String message, long clientID){
            if (!isGameActive){
            broadcast("There is no game active.", clientID);
            return;
        }
        if (!message.matches("guess \\d+")){
            handleCommand(message, clientID);
            broadcast("Invalid command, try 'guess 5'", clientID);
            return;
        }
        int numGuessed = Integer.parseInt(message.split(" ")[1]);
        if (numGuessed == hiddenNum){
            String winMessage = String.format("User[%d] guessed %d and it was correct!", clientID);
            broadcast(winMessage, clientID);
            endGame(clientID);
        }
        else{
            String loseMessage = String.format("User[%d] guessed %d but that is incorrect.", clientID);
            broadcast(loseMessage, -1);
        }
    }
        
    private void handleCommand(String message, long clientID){
        if(message.matches("start")){
            startGame(clientID);
        }
        if(message.matches("guess")){
            processGuess(message, clientID);
        } else {
            String defaultMessage = String.format("User[%d]: %s", clientID);
            broadcast(defaultMessage, -1);
    
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