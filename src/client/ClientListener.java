package client;

import java.util.List;

import server.content.*;

public interface ClientListener {
	
	public void readyNotPossible();
	
	public void ready(Player p);
	
	public void playedCard(Player p, Card c);
	
	public void wrotMessage(Player p, String m);
	
	public void playerCountChanged(List<Player> players);
	
	public void unready(Player p);
	
	public void idChanged(int id);
	
	public void gameTypeChanged(GameMode gt);
	
	public void hostChanged(Player p);
	
	
	
	
	
	
}
