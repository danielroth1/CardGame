package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import client.Client;

import server.content.Card;
import server.content.Command;
import server.content.DeckType;
import server.content.GameMode;
import server.content.Player;

public class GameLogic implements ServerListener{
	
	private List<GameLogicListener> glls = new LinkedList<GameLogicListener>();
	private List<Player> players = new LinkedList<Player>();
	private Player host = null;
	private List<Player> playersReady = new LinkedList<Player>();
	private boolean gameStarted = false;
	private Player playerHavingTurn = null;
	private Card topCard = null;
	private Map<Player, List<Card>> cardsOnHandMap = new HashMap<Player, List<Card>>();
	private List<Card> deck;
	private List<Card> layedCards;
	private List<Player> playersLost;
	private int startingCards = 5;
	
	public GameLogic(Server server) {
		server.addServerListener(this);
	}
	
	public void addGameLogicListener(GameLogicListener gll){
		glls.add(gll);
	}
	
	@Override
	public void hostJoined(Player p) {
		players.add(p);
		host = p;
		cardsOnHandMap.put(p, new LinkedList<Card>());
	}
	
	@Override
	public void playerJoined(Player p) {
		players.add(p);
		cardsOnHandMap.put(p, new LinkedList<Card>());
		
	}

	@Override
	public void playerReady(Player p) {
		if (!gameStarted){
			if (!playersReady.contains(p)){
				playersReady.add(p);
				for (GameLogicListener gll : glls){
					gll.ready(p);
				}
				System.out.println(playersReady.size());
				System.out.println(players.size());
				
				if (!players.isEmpty()
						&& playersReady.size() == players.size()){
					startGame();
				}
			}
		}
	}
	
	@Override
	public void playerUnready(Player p) {
		if (!gameStarted){
			playersReady.remove(p);
			for (GameLogicListener gll : glls){
				gll.unready(p);
			}
		}
	}
	
	@Override
	public void chatMessage(Player p, String message) {
		for (GameLogicListener gll : glls){
			gll.wroteMessage(p, message);
		}
		
	}

	@Override
	public void drawCard(Player p) {
		if (gameStarted
				&& !playersLost.contains(p)
				&& p == playerHavingTurn){
			Card card = popDeck();
			if (card != null){
				cardsOnHandMap.get(p).add(card); //top card of deck
				p.setCardsInHand(cardsOnHandMap.get(p).size());
			}
			else{
				//deck is empty -> shuffle layed cards
				Card topCard = layedCards.get(layedCards.size()-1);
				layedCards.remove(layedCards.size()-1);
				shuffleLayedCards();
				deck = layedCards;
				layedCards.clear();
				layedCards.add(topCard);
				card = popDeck();
				cardsOnHandMap.get(p).add(card); //top card of deck
				p.setCardsInHand(cardsOnHandMap.get(p).size());
				//TODO: layedCards empty -> all cards on hand
			}
			changeTurnToNextPlayer();
			for (GameLogicListener gll : glls){
				gll.drewCard(p, card);
				gll.setAmountOfCardsInDeck(deck.size());
			}
		}
		else{
			for (GameLogicListener gll : glls){
				gll.notInTurn(p);
			}
		}
		
	}
	
	@Override
	public void playCard(Player p, Card card) {
		if (gameStarted
				&& !playersLost.contains(p)
				&& p == playerHavingTurn){
			if (cardOnHand(p, card)
					&& cardIsLegal(card)){
				cardsOnHandMap.get(p).remove(card);
				p.setCardsInHand(cardsOnHandMap.get(p).size());
				for (GameLogicListener gll : glls){
					gll.playedCard(p, card);
				}
				topCard = card;
				if (cardsOnHandMap.get(p).isEmpty()){
					playerWon(p);
				}
				changeTurnToNextPlayer();
			}
			else{
				for (GameLogicListener gll : glls){
					gll.illegalCard(p, card);
				}
			}
		}
		else{
			for (GameLogicListener gll : glls){
				gll.notInTurn(p);
			}
		}

			
		
	}

	@Override
	public void surrender(Player p) {
		playersLost.add(p);
		players.remove(p);
		for (GameLogicListener gll : glls){
			gll.surrender(p);
		}
		for (GameLogicListener gll : glls){
			gll.lost(p);
		}
		
	}
	
	private void lost(Player p){
		playersLost.add(p);
		players.remove(p);
		for (GameLogicListener gll : glls){
			gll.lost(p);
		}
	}
	
	private boolean cardOnHand(Player p, Card c){
		List<Card> cardsOnHand = cardsOnHandMap.get(p);
		if (cardsOnHand.contains(c))
			return true;
		return false;
	}
	
	private boolean cardIsLegal(Card c){
		if (c == null)
			return true;
		if (c.isLegalCombination(topCard))
			return true;
		return false;
	}
	
	private void startGame(){
		initiateDeck();
		gameStarted = true;
		playerHavingTurn = players.get(0);
		playersLost = new LinkedList<Player>();
		layedCards = new LinkedList<Card>();
		for (Map.Entry<Player, List<Card>> entry : cardsOnHandMap.entrySet()){
			Player p = entry.getKey();
			List<Card> cards = entry.getValue();
			for (int i=0; i<startingCards; i++){
				Card card = popDeck();
				cards.add(card);
				for (GameLogicListener gll : glls){
					gll.drewCard(p, card);
				}
			}
		}
		//CARDS IN HAND
		for (Map.Entry<Player, List<Card>> entry : cardsOnHandMap.entrySet()){
			Player p = entry.getKey();
			List<Card> cards = entry.getValue();
			p.setCardsInHand(cards.size());
		}
		topCard = popDeck();
		for (GameLogicListener gll : glls){
			gll.gameStarted();
		}
		for (GameLogicListener gll : glls){
			gll.setTopCard(topCard);
			gll.setAmountOfCardsInDeck(deck.size());
		}
		for (GameLogicListener gll : glls){
			gll.playerHavingTurnChangedTo(players.get(0));
		}
	}
	
	private void shuffleLayedCards(){
		layedCards = Card.shuffleDeck(layedCards);
	}
	
	private void changeTurnToNextPlayer(){
		playerHavingTurn = getNextPlayer();
		playersHavingTurnChangedTo(playerHavingTurn);
	}
	
	private void playersHavingTurnChangedTo(Player p){
		for (GameLogicListener gll : glls){
			gll.playerHavingTurnChangedTo(playerHavingTurn);
		}
	}

	private Player getNextPlayer(){
		int i = players.indexOf(playerHavingTurn) + 1;
		if (i >= players.size())
			return players.get(0);
		return players.get(i);
	}
	
	private void initiateDeck(){
		deck = Card.getNewShuffledDeck();
	}
	
	/**
	 * remove last card from deck and return it
	 * @return top card of deck
	 */
	private Card popDeck(){
		if (deck.isEmpty())
			return null;
		Card c = deck.get(deck.size()-1);
		deck.remove(deck.size()-1);
		return c;
	}
	
	private void playerWon(Player p){
		for (GameLogicListener gll : glls){
			gll.won(p);
		}
		for (Player p2 : players)
			if (!p2.equals(p))
				for (GameLogicListener gll : glls){
					gll.lost(p2);
				}
	}

	@Override
	public void changeHost(Player p, Player newHost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pacing(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pass(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void raise(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveRoom(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeGameMode(Player p, GameMode gm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeDeckType(Player p, DeckType dt) {
		// TODO Auto-generated method stub
		
	}

	

	
}
