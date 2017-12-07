package client.gui.content;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.content.Player;

public class PlayerButton extends JPanel{
	
	public static final Dimension SIZE = new Dimension(100, 30);
	public static final Color HIGHLIGHT_COLOR = new Color(128, 128, 192);
	public static final Color LOWLIGHT_COLOR = new Color(255, 128, 64);
	
	
	private Player player;
	private int cardsInHand = 0;
	private JLabel nameLabel;
	
	
	public PlayerButton(Player player) {
		setForeground(Color.WHITE);
		cardsInHand = 5;
		setMinimumSize(SIZE);
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
		this.player = player;
		setBorder(BorderFactory.createRaisedBevelBorder());
		setBackground(LOWLIGHT_COLOR);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Box contentBox = new Box(BoxLayout.PAGE_AXIS);
		nameLabel = new JLabel(cardsInHand + player.getName());
//		nameLabel.setForeground(Color.WHITE);
		contentBox.add(nameLabel);
		
		add(Box.createVerticalGlue());
		add(contentBox);
		add(Box.createVerticalGlue());
		
		updateButton();
	}
	
	private void updateButton(){
		updateNameLabel();
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player){
		this.player = player;
		updateButton();
	}

	public int getCardsInHand() {
		return cardsInHand;
	}

	public void setCardsInHand(int cardsInHand) {
		this.cardsInHand = cardsInHand;
		updateNameLabel();
	}
	
	public void updateNameLabel(){
		nameLabel.setText(cardsInHand + " : " + player.getName());
	}
	
	public void highlightButton(){
		setBackground(HIGHLIGHT_COLOR);
		repaint();
	}
	
	public void lowlightButton(){
		setBackground(LOWLIGHT_COLOR);
		repaint();
	}
	
	
	

}
