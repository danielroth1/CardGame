package client.gui.content;

import server.content.Card;

public interface CardListener {
	
	public void drawCard();
	
	public void playCard(Card card);
	
	public void cardTouched(Card card);
	
	public void cardClicked(Card card);
}
