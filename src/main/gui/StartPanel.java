package main.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.logic.LogicControl;

public class StartPanel extends JPanel{
	
	private LogicControl logicControl;
	private JButton joinButton = new JButton("Join");
	private JButton hostButton = new JButton("Host");
	private JButton exitButton = new JButton("Exit");
	
	
	public StartPanel(LogicControl logicControl) {
		this.logicControl = logicControl;
		setOpaque(false);
		initiateButtons();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Box contentBox = new Box(BoxLayout.PAGE_AXIS);
		contentBox.add(joinButton);
		contentBox.add(Box.createRigidArea(new Dimension(0, 5)));
		contentBox.add(hostButton);
		contentBox.add(Box.createRigidArea(new Dimension(0, 5)));
		contentBox.add(exitButton);
		contentBox.setAlignmentX(CENTER_ALIGNMENT);
		
		add(Box.createVerticalGlue());
		add(contentBox);
		add(Box.createVerticalGlue());
		
	}
	
	private void initiateButtons(){
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logicControl.buttonPressed(LogicControl.BUTTON_START_JOIN);
			}
		});
		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logicControl.buttonPressed(LogicControl.BUTTON_START_HOST);
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logicControl.buttonPressed(LogicControl.BUTTON_START_EXIT);
			}
		});
	}

}
