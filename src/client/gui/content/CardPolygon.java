package client.gui.content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;

import server.content.Card;
import server.content.CardColor;
import server.content.CardType;

public class CardPolygon extends Polygon{
	
	public final static int CARD_WIDTH = 70;
	public final static int CARD_HEIGHT = 80;
	public static Color COLOR = new Color(231, 24, 30);
	
	private Card card;
	private CardType type;
	private CardColor color;
	private int width = CARD_WIDTH;
	private int heigth = CARD_HEIGHT;
	private int x;
	private int y;
	private boolean visible;
	
	private List<Point> positions;
	
	public CardPolygon(Card card, int x, int y, boolean visible) {
		this.card = card;
		type = card.getType();
		color = card.getColor();
		this.x = x;
		this.y = y;
		this.visible = visible;
		
		updateCoordinates();
		
		
	}
	
	private void updateCoordinates(){
		xpoints[0] = x;
		ypoints[0] = y;
		
		xpoints[1] = x + width;
		ypoints[1] = y;
		
		xpoints[2] = x + width;
		ypoints[2] = y + heigth;
		
		xpoints[3] = x;
		ypoints[3] = y + heigth;
		
		
		npoints = 4;
		
		calculatePositions(20, 20);
	}
	
	public void calculatePositions(int minWidth, int minHeigth){
		double w2 = width - (minWidth * 2);
		double h2 = heigth - (minHeigth * 2);
		if (w2<0
				|| h2<0){
			System.out.println("invalid bounds");
			return;
		}
		positions = new LinkedList<Point>();
		double wUnit = w2/4;
		double hUnit = h2/9;
		int x = this.x + minWidth;
		int y = this.y + minHeigth;
		positions.add(null);//0
		positions.add(new Point((int)(x+wUnit/2), (int)(y+hUnit/2)));//1
		positions.add(new Point((int)(x+wUnit*2), (int)(y+hUnit*2)));//2
		positions.add(new Point((int)(x+wUnit/2+3*wUnit), (int)(y+hUnit/2)));//3
		positions.add(new Point((int)(x+wUnit/2), (int)(y+hUnit/2+3*hUnit)));//4
		positions.add(new Point((int)(x+wUnit/2+3*wUnit), (int)(y+hUnit/2+3*hUnit)));//5
		positions.add(new Point((int)(x+wUnit/2), (int)(y+hUnit/2+4*hUnit)));//6
		positions.add(new Point((int)(x+2*wUnit), (int)(y+hUnit/2+4*hUnit)));//7
		positions.add(new Point((int)(x+wUnit/2+3*wUnit), (int)(y+hUnit/2+4*hUnit)));//8
		positions.add(new Point((int)(x+wUnit/2), (int)(y+hUnit/2+5*hUnit)));//9
		positions.add(new Point((int)(x+wUnit/2+3*wUnit), (int)(y+hUnit/2+5*hUnit)));//10
		positions.add(new Point((int)(x+wUnit/2), (int)(y+hUnit/2+8*hUnit)));//11
		positions.add(new Point((int)(x+2*wUnit), (int)(y+7*hUnit)));//12
		positions.add(new Point((int)(x+wUnit/2+3*wUnit), (int)(y+hUnit/2+8*hUnit)));//13
	}
	
	public void drawCard(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillPolygon(this);
		g2.setColor(Color.BLACK);
		g2.drawPolygon(this);
		if (!visible){
		g2.setColor(Color.BLACK);
		g2.drawRect(x+10, y+10, CARD_WIDTH-20, CARD_HEIGHT-20);
		g2.setColor(COLOR);
		g2.fillRect(x+10, y+10, CARD_WIDTH-20, CARD_HEIGHT-20);
		}
		else{
		if (color == CardColor.HEART
				|| color == CardColor.DIAMOND)
			g2.setColor(Color.RED);
		else
			g2.setColor(Color.BLACK);
		
		//EDGES
		char[] ca = new char[1];
		ca[0] = CardColor.getChar(color);
		g2.drawChars(ca, 0, 1, x+10, y+40);
		g2.drawChars(ca, 0, 1, x+width-10, y+heigth-40);
		byte[] b = CardType.getString(type).getBytes();
		g2.drawBytes(b, 0, 1, x+10, y+10);
		g2.drawBytes(b, 0, 1, x+width-10, y+heigth-10);
		
		//MID
		if (positions != null)
		switch(type){
		case TWO:
			drawChar(g2, ca, positions.get(2));
			drawChar(g2, ca, positions.get(12));
			break;
		case THREE:
			drawChar(g2, ca, positions.get(2));
			drawChar(g2, ca, positions.get(7));
			drawChar(g2, ca, positions.get(12));
			break;
		case FOUR:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(11));
			drawChar(g2, ca, positions.get(13));
			break;
		case FIFE:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(7));
			drawChar(g2, ca, positions.get(11));
			drawChar(g2, ca, positions.get(13));			
			break;
		case SIX:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(6));
			drawChar(g2, ca, positions.get(8));
			drawChar(g2, ca, positions.get(11));			
			drawChar(g2, ca, positions.get(13));			
			break;
		case SEVEN:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(2));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(6));
			drawChar(g2, ca, positions.get(8));
			drawChar(g2, ca, positions.get(11));			
			drawChar(g2, ca, positions.get(13));			
			break;
		case EIGHT:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(4));
			drawChar(g2, ca, positions.get(5));
			drawChar(g2, ca, positions.get(9));
			drawChar(g2, ca, positions.get(10));
			drawChar(g2, ca, positions.get(11));			
			drawChar(g2, ca, positions.get(13));			
			
			break;
		case NINE:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(4));
			drawChar(g2, ca, positions.get(5));
			drawChar(g2, ca, positions.get(7));
			drawChar(g2, ca, positions.get(9));
			drawChar(g2, ca, positions.get(10));
			drawChar(g2, ca, positions.get(11));			
			drawChar(g2, ca, positions.get(13));
			break;
		case TEN:
			drawChar(g2, ca, positions.get(1));
			drawChar(g2, ca, positions.get(3));
			drawChar(g2, ca, positions.get(4));
			drawChar(g2, ca, positions.get(5));
			drawChar(g2, ca, positions.get(6));
			drawChar(g2, ca, positions.get(8));
			drawChar(g2, ca, positions.get(9));
			drawChar(g2, ca, positions.get(10));
			drawChar(g2, ca, positions.get(11));			
			drawChar(g2, ca, positions.get(13));
			break;
		case JACK:
			drawChar(g2, ca, positions.get(7));
			break;
		case QUEEN:
			drawChar(g2, ca, positions.get(7));
			break;
		case KING:
			drawChar(g2, ca, positions.get(7));
			break;
		case ACE:
			drawChar(g2, ca, positions.get(7));
			break;
			
		}
		}
	}
	
	private void drawChar(Graphics2D g2, char[] ca, Point p){
		g2.drawChars(ca, 0, 1, p.x, p.y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeigth() {
		return heigth;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setWidth(int width) {
		this.width = width;
		updateCoordinates();
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
		updateCoordinates();
	}

	public void setX(int x) {
		this.x = x;
		updateCoordinates();
	}

	public void setY(int y) {
		this.y = y;
		updateCoordinates();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	
	

}
