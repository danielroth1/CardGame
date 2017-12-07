package client;

import java.util.List;

import server.content.Card;
import server.content.GameMode;
import server.content.Player;

public class ClientLogic implements ClientListener{
	
	private ClientInterface ci;
	
	public ClientLogic(Client client) {
		// TODO Auto-generated constructor stub
	}
	
	public void setClientInterface(ClientInterface ci){
		this.ci = ci;
		start();
	}
	
	private void start(){
		
	}

	@Override
	public void readyNotPossible() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ready(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playedCard(Player p, Card c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wrotMessage(Player p, String m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerCountChanged(List<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unready(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void idChanged(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameTypeChanged(GameMode gt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hostChanged(Player p) {
		// TODO Auto-generated method stub
		
	}

}
