package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import server.content.*;

import main.gui.CardPanel;
import main.gui.GuiControl;

import client.Client;
import client.gui.content.*;

public class ClientGui implements CardListener{
	
	public final static Dimension SIZE = new Dimension(400, 400);
	
	private Client client;
	private JPanel contentPanel;
	private GamePanel gamePanel;
	private PlayerPanel playerPanel;
	private ChatPanel chatPanel;
//	private CardPolygon c = new CardPolygon(new Card(CardType.NINE, CardColor.CLUB), 100, 100, true);
	
	
	public ClientGui(Client client) {
		this.client = client;
		initiatePanels();
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
//		contentPanel.setMinimumSize(SIZE);
		contentPanel.setPreferredSize(SIZE);
//		contentPanel.setMaximumSize(SIZE);
		gamePanel.setMinimumSize(new Dimension(400, 400));
		gamePanel.setPreferredSize(new Dimension(400, 400));
		Box contentBox = new Box(BoxLayout.PAGE_AXIS);
		Box panelBox = new Box(BoxLayout.LINE_AXIS);
		Box panelBox2 = new Box(BoxLayout.PAGE_AXIS);
		panelBox2.add(gamePanel);
		panelBox2.add(chatPanel);
		
		panelBox.add(panelBox2);
		panelBox.add(playerPanel);
		
		contentBox.add(panelBox);
		contentPanel.add(contentBox);
	}
	
	private void initiatePanels(){
		contentPanel = new CardPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = -565990690628383981L;

			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
			}
		};
		gamePanel = new GamePanel();
		gamePanel.addCardListener(this);
		playerPanel = new PlayerPanel();
		chatPanel = new ChatPanel();
	}
	
	public JPanel getPanel(){
		return contentPanel;
	}

	@Override
	public void drawCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playCard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardTouched(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardClicked(Card card) {
		// TODO Auto-generated method stub
		
	}

	public void addCardListener(CardListener cl) {
		gamePanel.addCardListener(cl);
	}

	public void update() {
		gamePanel.update();
	}

	public void update(Card topCard, List<Card> cardsInHand,
			int numberOfCardsInDeck) {
		gamePanel.update(topCard, cardsInHand, numberOfCardsInDeck);
	}

	public void updateDeckCard() {
		gamePanel.updateDeckCard();
	}

	public void updateTopCard(Card card) {
		gamePanel.updateTopCard(card);
	}

	public void updateNumberOfCardsInDeck(int numberOfCardsInDeck) {
		gamePanel.updateNumberOfCardsInDeck(numberOfCardsInDeck);
	}

	public void updateCardsInHand(List<Card> cardsInHand) {
		gamePanel.updateCardsInHand(cardsInHand);
	}

	public void updatePlayers(List<Player> players) {
		playerPanel.updatePlayers(players);
	}

	public void addText(String text) {
		System.out.println(text);
		chatPanel.addText(text);
	}

	public String getWrittenTextAndClear() {
		return chatPanel.getWrittenTextAndClear();
	}
	
	public void addItemListenerToReadyCheckBox(ItemListener il){
		playerPanel.addItemListener(il);
	}
	
	public void addKeyListenerToWriteField(KeyListener kl){
		chatPanel.addKeyListener(kl);
	}
	
	public void setPlayerOnTurnHighlighted(Player p){
		playerPanel.setPlayerHighlighted(p);
	}
	
	public void updatePlayersCardsInHand(){
		playerPanel.update();
	}
	
	public void setPlayersTurn(boolean hisTurn){
		playerPanel.setPlayersTurn(hisTurn);
	}
	
	
	

}
