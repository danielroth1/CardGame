package client;

import server.content.Card;
import server.content.Player;

public interface ClientInterface {
	
	public void setReady();
	
	public void setUnready();
	
	public void chatMessage(String message);
	
	public void drawCard();
	
	public void playCard(Card card);
	
	public void surrender();
	

}
