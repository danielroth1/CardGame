package server.content;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Card implements Serializable{
	
	private CardType type;
	private CardColor color;
	
	public Card(CardType type, CardColor color) {
		this.type = type;
		this.color = color;
	}
	
	public boolean isLegalCombination(Card c){
		if (type == c.getType()
				|| color == c.getColor())
			return true;
		System.out.println("illegal combination: " + type + " != " + c.getType());
		return false;
	}
	
	public static List<Card> getDeck(){
		List<Card> deck = new LinkedList<Card>();
		for (CardType t : CardType.values()){
			for (CardColor c : CardColor.values()){
				deck.add(new Card(t, c));
			}
		}
		System.out.println("Deck size: " + deck.size());
		return deck;
	}
	
	/**
	 * return a shuffled deck containing the cards of given deck
	 * NOTE: deck gets cleared in process
	 * @param deck given deck
	 * @return shuffled deck
	 */
	public static List<Card> shuffleDeck(List<Card> deck){
		List<Card> list = new LinkedList<Card>();
		final int size = deck.size();
		for (int i=0; i<size; i++){
			int random = (int)(Math.random() * deck.size());
			list.add(deck.get(random));
			deck.remove(random);
		}
		return list;
	}
	
	public static List<Card> getNewShuffledDeck(){
		return shuffleDeck(getDeck());
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card){
			Card c = (Card) obj;
			if (color == c.getColor()
					&& type == c.getType())
				return true;
		}
		return false;
	}

	public CardType getType() {
		return type;
	}

	public CardColor getColor() {
		return color;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public void setColor(CardColor color) {
		this.color = color;
	}
	
	

}
