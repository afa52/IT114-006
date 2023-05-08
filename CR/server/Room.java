package CR.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;

import CR.common.Constants;
import CR.common.MyLogger;
import CR.common.GeneralUtils;

public class Room implements AutoCloseable {
	private String name;
	protected List<ServerThread> clients = new ArrayList<ServerThread>();
	private boolean isRunning = false;

	// Commands
	private final static String COMMAND_TRIGGER = "/";
	private final static String WHISPER = "@";
	private final static String CREATE_ROOM = "createroom";
	private final static String JOIN_ROOM = "joinroom";
	private final static String DISCONNECT = "disconnect";
	private final static String LOGOUT = "logout";
	private final static String LOGOFF = "logoff";
	private final static String ROLL = "roll";
	private final static String FLIP = "flip";
	private final static String MUTE = "mute";
	private final static String UNMUTE = "unmute";

	private static MyLogger logger = MyLogger.getLogger(Room.class.getName());
	private HashMap<String, String> converter = null;

	public Room(String name) {
		this.name = name;
		isRunning = true;
	}

	private void info(String message) {
		logger.info(String.format("Room[%s]: %s", name, message));
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
			info("Attempting to add a client that already exists");
		} else {
			client.setFormattedName(String.format("<font color=\"%s\">%s</font>", GeneralUtils.getRandomHexColor(),
					client.getClientName()));
			clients.add(client);
			sendConnectionStatus(client, true);
			sendRoomJoined(client);
			sendUserListToClient(client);
		}
	}

	protected synchronized void removeClient(ServerThread client) {
		if (!isRunning) {
			return;
		}
		clients.remove(client);
		// we don't need to broadcast it to the server
		// only to our own Room
		if (clients.size() > 0) {
			// sendMessage(client, "left the room");
			sendConnectionStatus(client, false);
		}
		checkClients();
	}

	protected void checkClients() {
		// Cleanup if room is empty and not lobby
		if (!name.equalsIgnoreCase(Constants.LOBBY) && clients.size() == 0) {
			close();
		}
	}

	private boolean processCommands(String message, ServerThread client) {
		boolean wasCommand = false;

		try {
			if (message.startsWith(COMMAND_TRIGGER)) {
				String[] comm = message.split(COMMAND_TRIGGER);
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
					case ROLL: // afa52 4-21-23
						String[] rollArgs = message.split("\\s+");
						if (rollArgs.length == 2 && rollArgs[1].matches("\\d+d\\d+")) {
							String[] dice = rollArgs[1].split("d");
							int numDice = Integer.parseInt(dice[0]);
							int max = Integer.parseInt(dice[1]);
							int total = 0;
							for (int i = 0; i < numDice; i++) {
								total += (int) (Math.random() * max) + 1;
							}
							sendMessage(client, "<b><i><font color=green> rolled " + numDice + "d" + max + " and got "
									+ total + "</font></i></b>");
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
					case FLIP: // afa52 4-21-23
						double flip = Math.random();
						if (flip < 0.5) {
							result = "<b style=color:red><i>TAILS</i></b>";
						} else {
							result = "<b style=color:blue><i>HEADS</i></b>";
						}
						sendMessage(client, result);
						break;
					case MUTE: // afa52 05-05-2023
						String[] muted = comm2[1].split(", ");
						List<String> muteList = new ArrayList<String>();
						for (String user : muted) {
							if (!client.isMuted(user)) {
								client.mute(user);
								muteList.add(user);
								ServerThread targetClient = findClient(user);
								if (targetClient != null) {
									targetClient.sendMessage(client.getClientId(),
											"<i><font color=red>You have been muted by " + client.getClientName()
													+ "</font></i>");
								}
							}
						}
						break;
					case UNMUTE: // afa52 05-05-2023
						String[] unmuted = comm2[1].split(", ");
						List<String> unmuteList = new ArrayList<String>();
						for (String user : unmuted) {
							if (client.isMuted(user)) {
								client.unmute(user);
								unmuteList.add(user);
								ServerThread targetClient = findClient(user);
								if (targetClient != null) {
									targetClient.sendMessage(client.getClientId(),
											"<i><font color=green>You have been unmuted by " + client.getClientName()
													+ "</font></i>");
								}
							}
						}
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
			} else if (message.startsWith(WHISPER)) { // afa 52 4-21-23
				String[] comm = message.split(WHISPER);
				String part1 = comm[1];
				String[] comm2 = part1.split(" ");
				String name = comm2[0];
				logger.info(String.format("Whispered username " + name));
				wasCommand = true;
				client.sendMessage(client.getClientId(), message);
				synchronized (clients) {
					Iterator<ServerThread> iter = clients.iterator();
					while (iter.hasNext()) {
						ServerThread target = iter.next();
						logger.info(String.format("Checking username..." + target.getClientName()));
						if (name.equals(target.getClientName())) {
							boolean confirmSend = target.sendMessage(target.getClientId(), message);
							if (!confirmSend) {
								handleDisconnect(iter, client);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wasCommand;
	}

	// Command helper methods

	private ServerThread findClient(String name) {
		for (ServerThread client : clients) {
			if (client.getClientName().equalsIgnoreCase(name)) {
				return client;
			}
		}
		return null;
	}

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

	protected synchronized void sendMessage(ServerThread sender, String message) {
		if (!isRunning) {
			return;
		}
		info("Sending message to " + clients.size() + " clients");
		if (sender != null && processCommands(message, sender)) {
			// it was a command, don't broadcast
			return;
		}
		message = formatMessage(message);
		long from = (sender == null) ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
		Iterator<ServerThread> iter = clients.iterator();
		while (iter.hasNext()) {
			ServerThread client = iter.next();
			// send message if sender not muted
			if (!client.isMuted(sender.getClientName())) {
				boolean messageSent = client.sendMessage(from, message);
				if (!messageSent) {
					iter.remove();
				}
			}
		}
	}

	protected String formatMessage(String message) {
		String alteredMessage = message;

		// expect pairs ** -- __
		if (converter == null) {
			converter = new HashMap<String, String>();
			// user symbol => output text separated by |
			converter.put("\\*{2}", "<b>|</b>");
			converter.put("--", "<i>|</i>");
			converter.put("__", "<u>|</u>");
			converter.put("#r#", "<font color=\"red\">|</font>");
			converter.put("#g#", "<font color=\"green\">|</font>");
			converter.put("#b#", "<font color=\"blue\">|</font>");
		}
		for (Entry<String, String> kvp : converter.entrySet()) {
			if (GeneralUtils.countOccurencesInString(alteredMessage, kvp.getKey().toLowerCase()) >= 2) {
				String[] s1 = alteredMessage.split(kvp.getKey().toLowerCase());
				String m = "";
				for (int i = 0; i < s1.length; i++) {
					if (i % 2 == 0) {
						m += s1[i];
					} else {
						String[] wrapper = kvp.getValue().split("\\|");
						m += String.format("%s%s%s", wrapper[0], s1[i], wrapper[1]);
					}
				}
				alteredMessage = m;
			}
		}

		return alteredMessage;
	}

	protected synchronized void sendUserListToClient(ServerThread receiver) {
		info(String.format("Room[%s] Syncing client list of %s to %s", getName(), clients.size(),
				receiver.getClientName()));
		synchronized (clients) {
			Iterator<ServerThread> iter = clients.iterator();
			while (iter.hasNext()) {
				ServerThread clientInRoom = iter.next();
				if (clientInRoom.getClientId() != receiver.getClientId()) {
					boolean messageSent = receiver.sendExistingClient(clientInRoom.getClientId(),
							clientInRoom.getClientName(),
							clientInRoom.getFormattedName());
					// receiver somehow disconnected mid iteration
					if (!messageSent) {
						handleDisconnect(null, receiver);
						break;
					}
				}
			}
		}
	}

	protected synchronized void sendRoomJoined(ServerThread receiver) {
		boolean messageSent = receiver.sendRoomName(getName());
		if (!messageSent) {
			handleDisconnect(null, receiver);
		}
	}

	protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
		// converted to a backwards loop to help avoid concurrent list modification
		// due to the recursive sendConnectionStatus()
		// this should only be needed in this particular method due to the recusion

		if (clients == null) {
			return;
		}
		synchronized (clients) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				ServerThread client = clients.get(i);
				boolean messageSent = client.sendConnectionStatus(sender.getClientId(), sender.getClientName(),
						sender.getFormattedName(),
						isConnected);
				if (!messageSent) {
					clients.remove(i);
					info("Removed client " + client.getClientName());
					checkClients();
					sendConnectionStatus(client, false);
				}
			}
		}
	}

	protected synchronized void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
		if (iter != null) {
			iter.remove();
		}
		info("Removed client " + client.getClientName());
		checkClients();
		sendConnectionStatus(client, false);
		sendMessage(null, client.getClientName() + " disconnected");
	}

	public void close() {
		logger.info(getName() + " closing");
		Server.INSTANCE.removeRoom(this);
		isRunning = false;
		clients = null;
	}

}