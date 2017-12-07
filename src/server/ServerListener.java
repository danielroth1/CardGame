package server;

import server.content.Card;
import server.content.DeckType;
import server.content.GameMode;
import server.content.Player;

public interface ServerListener {
	
	public void hostJoined(Player p);
	
	public void playerJoined(Player p);
	
	public void playerReady(Player p);
	
	public void playerUnready(Player p);
	
	public void chatMessage(Player p, String message);
	
	public void drawCard(Player p);
	
	public void playCard(Player p, Card card);
	
	public void surrender(Player p);
	
	public void changeHost(Player p, Player newHost);
	
	public void pacing(Player p);
	
	public void pass(Player p);
	
	public void raise(Player p);
	
	public void leaveRoom(Player p);
	
	public void changeGameMode(Player p, GameMode gm);
	
	public void changeDeckType(Player p, DeckType dt);
	
}
