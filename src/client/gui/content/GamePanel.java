package client.gui.content;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import server.content.Card;

import main.gui.CardPanel;

public class GamePanel extends CardPanel{
	
	private List<CardListener> cls = new LinkedList<CardListener>();
	private Card topCard = null;
	private CardPolygon topCardPol = null;
	private CardPolygon deckCardPol;
	private int numberOfCardsInDeck = 0;
	private String numberOfCardsInDeckString = Integer.toHexString(numberOfCardsInDeck);
	private List<Card> cardsInHand = new LinkedList<Card>();
	private List<CardPolygon> cardsInHandPol = new LinkedList<CardPolygon>();
	private Dimension workSpace = new Dimension(500, 300);
	
	
	public GamePanel() {
		deckCardPol = new CardPolygon(new Card(null, null), workSpace.width/2+5, 
				workSpace.height/2-CardPolygon.CARD_HEIGHT-50, false);
		addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				JPanel p = (JPanel)e.getSource();
				setWorkSpace(p.getSize());
				}
		});
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Point p = e.getPoint();
				if (topCardPol != null
						&& topCardPol.contains(p)){
					for (CardListener cl : cls){
						cl.cardClicked(topCardPol.getCard());
					}
				}
				if (deckCardPol != null
						&& deckCardPol.contains(p)){
					for (CardListener cl : cls){
						cl.drawCard();
						cl.cardClicked(deckCardPol.getCard());
					}
				}
				for (CardPolygon cp : cardsInHandPol){
					if (cp.contains(p)){
						for (CardListener cl : cls){
							cl.cardClicked(cp.getCard());
							cl.playCard(cp.getCard());
						}
					}
				}
					
			}
		});
		addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				Point p = e.getPoint();
				if (topCardPol != null
						&& topCardPol.contains(p)){
					for (CardListener cl : cls){
						cl.cardTouched(topCardPol.getCard());
					}
				}
				if (deckCardPol != null
						&& deckCardPol.contains(p)){
					for (CardListener cl : cls){
						cl.cardTouched(deckCardPol.getCard());
					}
				}
				for (CardPolygon cp : cardsInHandPol){
					if (cp.contains(p)){
						for (CardListener cl : cls){
							cl.cardTouched(cp.getCard());
						}
					}
				}
				
			}
		});
	}
	
	public void addCardListener(CardListener cl){
		cls.add(cl);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (topCardPol != null){
			topCardPol.drawCard(g2);
		}
		if (deckCardPol != null)
			deckCardPol.drawCard(g2);
		g2.drawString(numberOfCardsInDeckString, 
				workSpace.width/2+10+CardPolygon.CARD_WIDTH, 
				workSpace.height/2-CardPolygon.CARD_HEIGHT/2-50);
		for (CardPolygon cp : cardsInHandPol){
			cp.drawCard(g2);
		}
		
	}
	
	public void update(){
		updateTopCard(topCard);
		updateCardsInHand(cardsInHand);
		updateDeckCard();
		updateNumberOfCardsInDeck(numberOfCardsInDeck);
	}
	
	public void update(Card topCard, List<Card> cardsInHand, int numberOfCardsInDeck){
		updateTopCard(topCard);
		updateCardsInHand(cardsInHand);
		updateDeckCard();
		updateNumberOfCardsInDeck(numberOfCardsInDeck);
	}
	
	public void updateDeckCard(){
		deckCardPol = new CardPolygon(new Card(null, null), workSpace.width/2+5, 
				workSpace.height/2-CardPolygon.CARD_HEIGHT-50, false);
		repaint();
	}
	
	public void updateTopCard(Card card){
		if (card != null){
			topCard = card;
			topCardPol = new CardPolygon(card, workSpace.width/2-5-CardPolygon.CARD_WIDTH, 
					workSpace.height/2-CardPolygon.CARD_HEIGHT-50, true);
			repaint();
		}
		else
			topCardPol = null;
	}
	
	public void updateNumberOfCardsInDeck(int numberOfCardsInDeck){
		this.numberOfCardsInDeck = numberOfCardsInDeck;
		this.numberOfCardsInDeckString = Integer.toString(numberOfCardsInDeck);
		repaint();
	}
	
	public void updateCardsInHand(List<Card> cardsInHand){
		this.cardsInHand = cardsInHand;
		cardsInHandPol.clear();
		if (!cardsInHand.isEmpty()){
			int y = workSpace.height/2+20;
			int xUnit = (workSpace.width-CardPolygon.CARD_WIDTH)/cardsInHand.size();
			int xStatic = 0;
			for(int i=0; i<cardsInHand.size(); i++){
				Card card = cardsInHand.get(i);
				cardsInHandPol.add(new CardPolygon(card, xUnit*i+xUnit/2+xStatic, y, true));
			}
		}
		repaint();
	}

	public Dimension getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(Dimension workSpace) {
		this.workSpace = workSpace;
		update();
	}
	
	

}
