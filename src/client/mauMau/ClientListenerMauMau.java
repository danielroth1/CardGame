package client.mauMau;

import client.ClientListenerGame;
import server.content.Card;
import server.content.Player;

public interface ClientListenerMauMau extends ClientListenerGame{
	
	public void drewCard(Player p, Card c);
	
	public void amountOfCardsInDeckIsSet(int amount);
	
	public void topCardChanged(Card card);
	
	

}
