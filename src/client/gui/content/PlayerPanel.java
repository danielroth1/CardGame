package client.gui.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.gui.GuiControl;

import server.content.Player;

public class PlayerPanel extends JPanel{
	
	private JLabel playerLabel = new JLabel("Players:");
	private JLabel turnLabel = new JLabel("It's your turn!");
	private JCheckBox readyCheckBox = new JCheckBox("ready");
	private List<Player> players = new LinkedList<Player>();
	private List<PlayerButton> playerButtons = new LinkedList<PlayerButton>();
	private Box contentBox = new Box(BoxLayout.PAGE_AXIS);
	private Box playerBox = new Box(BoxLayout.PAGE_AXIS);
	
	public PlayerPanel() {
		setBackground(GuiControl.BACKGROUND_COLOR_BRIGHT);
		setForeground(Color.WHITE);
		playerLabel.setForeground(Color.WHITE);
		readyCheckBox.setBackground(GuiControl.BACKGROUND_COLOR_BRIGHT);
		readyCheckBox.setForeground(Color.WHITE);
		turnLabel.setVisible(false);
		turnLabel.setForeground(new Color(0, 255, 0));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		contentBox = new Box(BoxLayout.PAGE_AXIS);
		playerBox = new Box(BoxLayout.PAGE_AXIS);
		contentBox.add(playerLabel);
		contentBox.add(playerBox);
		
		add(contentBox);
		add(Box.createVerticalGlue());
		add(turnLabel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(readyCheckBox);
		
	}
	
	public void addItemListener(ItemListener il){
		readyCheckBox.addItemListener(il);
	}
	
	public void revalidatePanel(){
		playerBox.removeAll();
		playerButtons.clear();
		revalidate();
		for (Player p : players){
			PlayerButton b = new PlayerButton(p);
			playerButtons.add(b);
			b.setCardsInHand(p.getCardsInHand());
			playerBox.add(b);
		}
		revalidate();
	}
	
	public void updateCardsInHand(){
		for (int i=0; i<players.size(); i++){
			Player p = players.get(i);
			if (playerButtons.size()>i){
				PlayerButton b = playerButtons.get(i);
				b.setCardsInHand(p.getCardsInHand());
			}
		}
	}
	
	public void update(){
		for (int i=0; i<playerButtons.size(); i++){
			PlayerButton pb = playerButtons.get(i);
			Player p = players.get(i);
			pb.setCardsInHand(p.getCardsInHand());
			pb.setPlayer(p);
		}
		repaint();
	}
	
	public void updatePlayers(List<Player> players){
		this.players = players;
		revalidatePanel();
	}
	
	public void setPlayerHighlighted(Player p){

		lowlightAll();
		for (int i=0; i<players.size(); i++){
			Player p2 = players.get(i);
			if (p2.equals(p)){
				playerButtons.get(i).highlightButton();
			}
		}
		repaint();
	}
	
	private void lowlightAll(){
		for (PlayerButton pb : playerButtons){
			pb.lowlightButton();
		}
	}
	
	public void setPlayersTurn(boolean hisTurn){
		turnLabel.setVisible(hisTurn);
	}

}
