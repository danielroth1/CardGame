package client;

import java.util.List;

import server.content.Card;
import server.content.Player;

public interface ClientListenerGame {
	
	public void notInTurn();
	
	public void illegalCard(Card c);
	
	public void surrendered(Player p);
	
	public void lost(Player p);
	
	public void turnChangedTo(Player p);
	
	public void won(Player p);
	
	public void gameStarted(List<Player> players);

}
