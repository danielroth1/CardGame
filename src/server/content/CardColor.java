package server.content;

import java.io.Serializable;

public enum CardColor implements Serializable{
	SPADE, HEART, DIAMOND, CLUB;
	
	public static char getChar(CardColor cc){
		switch(cc){
		case SPADE:
			return '\u2660';
		case HEART:
			return '\u2665';
		case DIAMOND:
			return '\u2666';
		case CLUB:
			return '\u2663';
		default:
			return 0;
		}
	}
}
