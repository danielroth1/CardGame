package main.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class CardPanel extends JPanel{

	public CardPanel() {
		setBackground(new Color(0, 128, 64));
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 200, 0));
		for (int i=0; i<GuiControl.FRAME_SIZE.width/50; i++){
			g2.drawLine(i*50, 0, i*50+GuiControl.FRAME_SIZE.height, GuiControl.FRAME_SIZE.height);
			g2.drawLine(0, i*50, Math.abs(GuiControl.FRAME_SIZE.height-i*50), GuiControl.FRAME_SIZE.height);
			g2.drawLine(i*50, GuiControl.FRAME_SIZE.height, GuiControl.FRAME_SIZE.height + i*50, 0);
			g2.drawLine(i*50, 0, i*50-GuiControl.FRAME_SIZE.height, GuiControl.FRAME_SIZE.height);
			
		}
	}
	
	

}
