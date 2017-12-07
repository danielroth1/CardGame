package client;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import server.Server;
import server.content.Card;
import server.content.Command;
import server.content.Player;
import client.gui.ClientGui;
import client.gui.content.CardAdapter;

public class Client implements ClientInterface{
	
	public final static int MESSAGE_NOT_IN_TURN = 0;
	public final static int MESSAGE_READY_NOT_POSSIBLE = 1;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_READY = 2;
	/**
	 * Obj1 instanceof Player
	 * Obj2 instanceof Card
	 */
	public final static int MESSAGE_DREW_CARD = 3;
	/**
	 * Obj1 instanceof Card
	 */
	public final static int MESSAGE_ILLEGAL_CARD = 4;
	/**
	 * Obj1 instanceof Player
	 * Obj2 instanceof Card
	 */
	public final static int MESSAGE_PLAYED_CARD = 5;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_SURRENDER = 6;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_LOST = 7;
	/**
	 * Obj1 instanceof Player
	 * Obj2 instanceof String
	 */
	public final static int MESSAGE_WROTE_MESSAGE = 8;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_HAVING_TURN_CHANGED_TO = 9;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_WON = 10;
	/**
	 * Obj1 instanceof List<Player> currentPlayersLoggedIn
	 */
	public final static int MESSAGE_PLAYER_COUNT_CHANGED = 11;
	/**
	 * Obj1 instanceof List<Player> players with updated cards on hand
	 */
	public final static int MESSAGE_GAME_STARTED = 12;
	/**
	 * DEPRICATED
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_TURN_CHANGED = 13;
	/**
	 * Obj1 instanceof Player
	 */
	public final static int MESSAGE_UNREADY = 14;
	/**
	 * Obj1 instanceof Integer amount of cards
	 */
	public final static int MESSAGE_AMOUNT_OF_CARDS_IN_DECK_IS_SET = 15;
	/**
	 * Obj1 instanceof Card top card
	 */
	public final static int MESSAGE_TOP_CARD_CHANGE = 16;
	/**
	 * Obj1 instanceof Integer new ID
	 */
	public final static int MESSAGE_ID_CHANGED = 17;
	/**
	 * Obj1 instanceof GameType new GameType
	 */
	public final static int MESSAGE_GAME_TYPE_CHANGED = 18;
	/**
	 * Obj1 instanceof Player new Host
	 */
	public final static int MESSAGE_HOST_CHANGED = 19;
	
	
	private ClientGui clientGui;
	private ClientListener cl; //listens for incomming commands from server
	private Player player;
	private ServerThread st;
	private Socket socket;
	private Map<Player, Integer> cardsInHandMap = new HashMap<Player, Integer>();
	private List<Player> players = new LinkedList<Player>();
	private List<Card> cardsInHand = new LinkedList<Card>();
	private int cardsInDeck = 0;
	private Card topCard = null;
	
	public Client(String name, String ip, int host) {
		ClientLogic clientLogic = new ClientLogic(this);
		//clientLogic gets methods to send commands to server
		//the Client class is the listener, that listens for commands from the logic
		//so that it can simply sent them as command objects to the server
		//in this way its not directly a listener but more a platform to communicate with
		//the server. The ClientLogic is of course responsible for the gui.
		clientLogic.setClientInterface(this);
		cl = clientLogic;
		clientGui = new ClientGui(this);
		clientGui.addCardListener(new CardAdapter(){
			public void playCard(Card c){
				Client.this.playCard(c);
			}
			
			public void drawCard(){
				Client.this.drawCard();
			}
		});
		clientGui.addItemListenerToReadyCheckBox(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					setUnready();
				else
					setReady();
			}
			
		});
		clientGui.addKeyListenerToWriteField(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					String s = clientGui.getWrittenTextAndClear();
					if (!s.isEmpty())
						chatMessage(s);
				}
			}
		});
		player = new Player(name, 0);
		try {
			socket = new Socket(ip, host);
		} catch (IOException e) {
			System.out.println("error while connecting to server");
			e.printStackTrace();
		}
		st = new ServerThread(this, socket);
		System.out.println("client connected");
	}

	public Player getPlayer() {
		return player;
	}
	
	public JPanel getPanel(){
		return clientGui.getPanel();
	}
	
	public void receivedObject(Object obj){
		if (obj instanceof List<?>){
			List<?> list = (List<?>) obj;
			for (Object o : list){
				if (o instanceof Command){
					Command c = (Command) o;
					receivedCommand(c);
				}
			}
		}
	}
	
	private void receivedCommand(Command com){
		int message = com.getMessage();
		switch(message){
		case MESSAGE_DREW_CARD:
			Player p = (Player)com.getObj1();
			Card c = null;
			System.out.println("drew card");
			if (p.equals(player)){
				c = (Card)com.getObj2();
				cardsInHand.add(c);
				clientGui.updateCardsInHand(cardsInHand);
			}
//			for (Player player : players){
//				if (p.equals(player)){
//					player.setCardsInHand(player.getCardsInHand()+1);
//				}
//			}
			for (Player player : players){
				if (player.equals(p))
					player.setCardsInHand(p.getCardsInHand());
			}
			clientGui.updatePlayersCardsInHand();
			
			break;
		case MESSAGE_HAVING_TURN_CHANGED_TO:
			Player p2 = (Player)com.getObj1();
			clientGui.addText(p2.getName() + " has the turn!");
			clientGui.setPlayerOnTurnHighlighted(p2);
			if (p2.equals(player))
				clientGui.setPlayersTurn(true);
			else
				clientGui.setPlayersTurn(false);
			break;
		case MESSAGE_ILLEGAL_CARD:
			Card c3 = (Card)com.getObj1();
			clientGui.addText("The card, you are trying to set, is illegal!");
			break;
		case MESSAGE_LOST:
			Player p4 = (Player)com.getObj1();
			clientGui.addText(p4.getName() + " lost!");
			
			break;
		case MESSAGE_NOT_IN_TURN:
			System.out.println("not in turn");
			clientGui.addText("Wait till it's your turn!");
			
			break;
		case MESSAGE_PLAYED_CARD:
			Player p5 = (Player) com.getObj1();
			Card c5 = (Card) com.getObj2();
			topCard = c5;
			cardsInHand.remove(c5);
			clientGui.updateCardsInHand(cardsInHand);
			clientGui.updateTopCard(topCard);
			for (Player player : players){
				if (player.equals(p5))
					player.setCardsInHand(p5.getCardsInHand());
			}
			clientGui.updatePlayersCardsInHand();
			
			break;
		case MESSAGE_PLAYER_COUNT_CHANGED:
			List<?> players = (List<?>)com.getObj1();
			this.players = new LinkedList<Player>();
			for (Object o6 : players){
				Player p6 = (Player) o6;
				this.players.add(p6);
			}
			clientGui.updatePlayers(this.players);
			break;
		case MESSAGE_READY:
			Player p7 = (Player) com.getObj1();
			clientGui.addText(p7.getName() + " is ready now");
			break;
		case MESSAGE_READY_NOT_POSSIBLE:
			clientGui.addText("You are already ready");
			
			break;
		case MESSAGE_SURRENDER:
			Player p8 = (Player)com.getObj1();
			clientGui.addText(p8 + " surrendered!");
			
			break;
		case MESSAGE_WON:
			Player p9 = (Player)com.getObj1();
			clientGui.addText(p9.getName() + " has won!");
			
			break;
		case MESSAGE_WROTE_MESSAGE:
			Player p10 = (Player)com.getObj1();
			String m = (String)com.getObj2();
			clientGui.addText(p10.getName() + ": " + m);
			break;
		case MESSAGE_GAME_STARTED:
			List<?> list = (List<?>) com.getObj1();
			for (Object o : list){
				if(o instanceof Player){
					Player player = (Player) o;
					for (Player player2 : Client.this.players){
						if (player.equals(player2))
							player2.setCardsInHand(player.getCardsInHand());
					}
				}
			}
			clientGui.addText("Game started!");
			clientGui.updatePlayersCardsInHand();
			break;
		case MESSAGE_AMOUNT_OF_CARDS_IN_DECK_IS_SET:
			cardsInDeck = (Integer) com.getObj1();
			clientGui.updateNumberOfCardsInDeck(cardsInDeck);
			break;
		case MESSAGE_TOP_CARD_CHANGE:
			topCard = (Card) com.getObj1();
			clientGui.updateTopCard(topCard);
			System.out.println("update top card " + topCard.getType() + " " + topCard.getColor());
			break;
		case MESSAGE_ID_CHANGED:
			player.setID((Integer)com.getObj1());
			break;
		case MESSAGE_UNREADY:
			Player p11 = (Player) com.getObj1();
			clientGui.addText(p11.getName() + " isn't ready anymore");
			break;
		}
	}

	@Override
	public void setReady() {
		Command c = new Command(Server.MESSAGE_READY);
		st.writeObject(c);
	}

	@Override
	public void chatMessage(String message) {
		Command c = new Command(Server.MESSAGE_CHAT_MESSAGE);
		c.setObj1(message);
		st.writeObject(c);
	}

	@Override
	public void drawCard() {
		Command c = new Command(Server.MESSAGE_DRAW_CARD);
		st.writeObject(c);
	}

	@Override
	public void playCard(Card card) {
		Command c = new Command(Server.MESSAGE_PLAY_CARD);
		c.setObj1(card);
		st.writeObject(c);
	}

	@Override
	public void surrender() {
		Command c = new Command(Server.MESSAGE_SURRENDER);
		st.writeObject(c);
	}

	@Override
	public void setUnready() {
		Command c = new Command(Server.MESSAGE_UNREADY);
		st.writeObject(c);
		
	}
	
	
	

}
