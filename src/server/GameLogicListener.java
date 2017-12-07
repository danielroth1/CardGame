package server;

import server.content.*;

public interface GameLogicListener {
	
	public void notInTurn(Player p);
	
	public void readyNotPossible(Player p);
	
	public void ready(Player p);
	
	public void unready(Player p);
	
	public void drewCard(Player p, Card card);
	
	public void illegalCard(Player p, Card c);
	
	public void playedCard(Player p, Card c);
	
	public void surrender(Player p);
	
	public void lost(Player p);
	
	public void won(Player p);
	
	public void wroteMessage(Player p, String message);
	
	public void gameStarted();
	
	public void playerHavingTurnChangedTo(Player p);
	
	public void setAmountOfCardsInDeck(int cardsInDeck);
	
	public void setTopCard(Card c);

}
