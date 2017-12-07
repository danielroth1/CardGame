package server.content;

import java.io.Serializable;

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4081046841188515775L;
	private String name;
	private int cardsInHand;
	private int ID;
	
	public Player(String name, int cardsInHand) {
		this.name = name;
		this.cardsInHand = cardsInHand;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player){
			Player p = (Player) obj;
			if (p.getID() == ID)
				return true;
		}
		return false;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCardsInHand() {
		return cardsInHand;
	}

	public void setCardsInHand(int cardsInHand) {
		this.cardsInHand = cardsInHand;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
	
}
