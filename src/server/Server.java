package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import client.Client;

import server.content.*;
import server.gui.ServerGui;

public class Server implements GameLogicListener{
	
	public final static int BUTTON_SHUT_DOWN = 0;
	public final static int BUTTON_START_GAME = 1;
	public final static int MESSAGE_READY = 2;
	/**
	 * Obj1 instanceof String
	 */
	public final static int MESSAGE_CHAT_MESSAGE = 3;
	public final static int MESSAGE_DRAW_CARD = 4;
	/**
	 * Obj1 instanceof Card
	 */
	public final static int MESSAGE_PLAY_CARD = 5;
	public final static int MESSAGE_SURRENDER = 6;
	public final static int MESSAGE_UNREADY = 7;
	/**
	 * Obj1 instanceof Player new Host
	 */
	public final static int MESSAGE_CHANGE_HOST = 8;
	/**
	 * Obj1 instanfeof Integer amount
	 */
	public final static int MESSAGE_RAISE = 9;
	public final static int MESSAGE_PACING = 10;
	public final static int MESSAGE_PASS = 11;
	public final static int MESSAGE_LEAVE_ROOM = 12;
	/**
	 * Obj1 instanceof GameMode new GameMode
	 */
	public final static int MESSAGE_CHANGE_GAME_MODE = 13;
	/**
	 * Obj1 instanceof DeckType
	 */
	public final static int MESSAGE_CHANGE_DECK_TYPE = 14;
	
	
	
	
	private GameLogic gameLogic;
	private ServerGui serverGui;
	private int port;
	private ServerSocket serverSocket;
	private Thread thread;
	private boolean running = true;
	private List<ClientThread> cts = new LinkedList<ClientThread>();
	private List<ServerListener> sls = new LinkedList<ServerListener>();
	private Map<Player, ClientThread> playerMap = new HashMap<Player, ClientThread>();
	private Thread threadCommandsOut;
	private int idCounter = 1;
	
	
	public Server(int port) {
		serverGui = new ServerGui(this);
		gameLogic = new GameLogic(this);
		gameLogic.addGameLogicListener(this);
		this.port = port;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("error while creating server socket");
			e.printStackTrace();
		}
		thread = new Thread(){
			public void run(){
				while(running){
					Socket s;
					try {
						s = serverSocket.accept();
						ClientThread ct = new ClientThread(Server.this, s);
						Player p = ct.getPlayer();
						p.setID(getNewId());
						Command c2 = new Command(Client.MESSAGE_ID_CHANGED);
						c2.setObj1(p.getID());
						ct.getCommandsOut().add(c2);
						playerMap.put(p, ct);
						cts.add(ct);
						serverGui.updateClientThreads(cts);
						Command c = new Command(Client.MESSAGE_PLAYER_COUNT_CHANGED);
						c.setObj1(new LinkedList<Player>(playerMap.keySet()));
						if (cts.size() == 1)
							gameLogic.hostJoined(ct.getPlayer());
						else
							gameLogic.playerJoined(ct.getPlayer());
						Server.this.sendToAll(c);
					} catch (IOException e) {
						System.out.println("error while accepting clients socket");
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
		threadCommandsOut = new Thread(){
			public void run(){
				while(running){
					for (ClientThread ct : cts){
						List<Command> out = ct.getCommandsOut();
						if (!out.isEmpty())
							ct.writeObject(out);
						out.clear();
						
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		};
		threadCommandsOut.start();
	}
	
	public void stop(){
		running = false;
	}
	
	public void objectReceived(ClientThread ct, Object obj){
		if (obj instanceof Command){
			Command c = (Command) obj;
			switch(c.getMessage()){
			case MESSAGE_READY:
				for (ServerListener sl : sls){
					sl.playerReady(ct.getPlayer());
				}
				break;
			case MESSAGE_CHAT_MESSAGE:
				for (ServerListener sl : sls){
					if (c.getObj1() instanceof String){
						String s = (String)c.getObj1();
						sl.chatMessage(ct.getPlayer(), s);
					}
					else{
						System.out.println("illegal message/ message not found");
					}
				}
				break;
			case MESSAGE_DRAW_CARD:
				for (ServerListener sl : sls){
					sl.drawCard(ct.getPlayer());
				}
				break;
			case MESSAGE_PLAY_CARD:
				for (ServerListener sl : sls){
					if (c.getObj1() instanceof Card){
						Card card = (Card)c.getObj1();
						sl.playCard(ct.getPlayer(), card);
					}
					else{
						System.out.println("no card object sent");
					}
				}
				break;
			case MESSAGE_SURRENDER:
				for (ServerListener sl : sls){
					sl.surrender(ct.getPlayer());
				}
				break;
			case MESSAGE_UNREADY:
				for (ServerListener sl : sls){
					sl.playerUnready(ct.getPlayer());
				}
				break;
			case MESSAGE_CHANGE_HOST:
				Player p = (Player) c.getObj1();
				
				break;
			case MESSAGE_PACING:
				
				break;
			case MESSAGE_PASS:
				
				break;
			case MESSAGE_RAISE:
				int amount = (Integer) c.getObj1();
				
				break;
			case MESSAGE_LEAVE_ROOM:
				
				break;
			case MESSAGE_CHANGE_GAME_MODE:
				GameMode gm = (GameMode) c.getObj1();
				
				break;
			case MESSAGE_CHANGE_DECK_TYPE:
				DeckType dt = (DeckType) c.getObj1();
				
				break;
			}
		}
	}
	
	public JPanel getPanel(){
		return serverGui.getPanel();
	}
	
	private void startGame(){
		
	}
	
	public void addServerListener(ServerListener serverListener){
		sls.add(serverListener);
	}
	
	
	public void buttonPressed(int button){
		switch(button){
		case BUTTON_SHUT_DOWN:
			stop();
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("error while closing socket");
				e.printStackTrace();
			}
			break;
		case BUTTON_START_GAME:
			startGame();
			break;
		}
	}

	@Override
	public void notInTurn(Player p) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_NOT_IN_TURN);
		ct.getCommandsOut().add(c);
	}

	@Override
	public void readyNotPossible(Player p) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_READY_NOT_POSSIBLE);
		ct.getCommandsOut().add(c);
	}

	@Override
	public void ready(Player p) {
		Command c = new Command(Client.MESSAGE_READY);
		c.setObj1(p);
		sendToAll(c);
	}
	
	@Override
	public void unready(Player p) {
		Command c = new Command(Client.MESSAGE_UNREADY);
		c.setObj1(p);
		sendToAll(c);
	}

	@Override
	public void drewCard(Player p, Card card) {
		Command c = new Command(Client.MESSAGE_DREW_CARD);
		ClientThread ct2 = playerMap.get(p);
		c.setObj1(p);
//		c.setObj2(card);
		for (ClientThread ct : cts){
			if (ct != ct2)
				ct.getCommandsOut().add(c);
		}
		Command c2 = new Command(Client.MESSAGE_DREW_CARD);
		c2.setObj1(p);
		c2.setObj2(card);
		ct2.getCommandsOut().add(c2);
		
	}
	
	@Override
	public void illegalCard(Player p, Card card) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_ILLEGAL_CARD);
		c.setObj1(card);
		ct.getCommandsOut().add(c);
		
	}

	@Override
	public void playedCard(Player p, Card card) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_PLAYED_CARD);
		c.setObj1(p);
		c.setObj2(card);
		sendToAll(c);
	}

	@Override
	public void surrender(Player p) {
		Command c = new Command(Client.MESSAGE_SURRENDER);
		c.setObj1(p);
		sendToAll(c);
	}

	@Override
	public void lost(Player p) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_LOST);
		c.setObj1(p);
		sendToAll(c);
	}

	@Override
	public void wroteMessage(Player p, String message) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_WROTE_MESSAGE);
		c.setObj1(p);
		c.setObj2(message);
		sendToAll(c);
	}

	@Override
	public void gameStarted() {
		Command c = new Command(Client.MESSAGE_GAME_STARTED);
		c.setObj1(new LinkedList<Player>(playerMap.keySet()));
		sendToAll(c);
	}

	@Override
	public void playerHavingTurnChangedTo(Player p) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_HAVING_TURN_CHANGED_TO);
		c.setObj1(p);
		sendToAll(c);
	}

	@Override
	public void won(Player p) {
		ClientThread ct = playerMap.get(p);
		Command c = new Command(Client.MESSAGE_WON);
		c.setObj1(p);
		sendToAll(c);
	}
	
	private void sendToAll(Command c){
		for (ClientThread ct : cts){
			ct.getCommandsOut().add(c);
		}
	}

	@Override
	public void setAmountOfCardsInDeck(int cardsInDeck) {
		Command c = new Command(Client.MESSAGE_AMOUNT_OF_CARDS_IN_DECK_IS_SET);
		c.setObj1(cardsInDeck);
		sendToAll(c);
		
	}

	@Override
	public void setTopCard(Card card) {
		Command c = new Command(Client.MESSAGE_TOP_CARD_CHANGE);
		c.setObj1(card);
		sendToAll(c);
		
	}
	
	private int getNewId(){
		idCounter ++;
		return idCounter;
	}
	
}
