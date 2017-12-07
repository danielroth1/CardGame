package server.content;

import java.io.Serializable;

public enum CardType implements Serializable{
	TWO, THREE, FOUR, FIFE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
	
	public static String getString(CardType ct){
		switch(ct){
		case TWO:
			return "2";
		case THREE:
			return "3";
		case FOUR:
			return "4";
		case FIFE:
			return "5";
		case SIX:
			return "6";
		case SEVEN:
			return "7";
		case EIGHT:
			return "8";
		case NINE:
			return "9";
		case TEN:
			return "10";
		case JACK:
			return "J";
		case QUEEN:
			return "Q";
		case KING:
			return "K";
		case ACE:
			return "A";
			
		}
		return "";
			
	}
}
