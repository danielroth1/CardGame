package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.logic.LogicControl;

public class ConnectPanel extends JPanel{
	
	private LogicControl logicControl;
	private JLabel ipPortLabel = new JLabel("Ip/Port : ");
	private JTextField ipField = new JTextField();
	private JTextField portField = new JTextField();
	private JButton exitButton = new JButton("Back");
	private JButton connectButton = new JButton("Connect");
	
	public ConnectPanel(LogicControl logicControl) {
		this.logicControl = logicControl;
		setOpaque(false);
		initiateButtons();
		
		ipPortLabel.setForeground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Box contentBox = new Box(BoxLayout.PAGE_AXIS);
		ipField.setMaximumSize(new Dimension(200, 25));
		portField.setMaximumSize(new Dimension(50, 25));
		
		Box ipBox = new Box(BoxLayout.LINE_AXIS);
		ipBox.add(ipPortLabel);
		ipBox.add(Box.createRigidArea(new Dimension(5, 0)));
		ipBox.add(ipField);
		ipBox.add(Box.createRigidArea(new Dimension(5, 0)));
		ipBox.add(portField);
		contentBox.add(ipBox);
		contentBox.add(Box.createRigidArea(new Dimension(0, 5)));
		
		Box buttonBox = new Box(BoxLayout.LINE_AXIS);
		buttonBox.add(exitButton);
		buttonBox.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonBox.add(connectButton);
		contentBox.add(buttonBox);
		
		add(Box.createVerticalGlue());
		add(contentBox);
		add(Box.createVerticalGlue());
		
	}
	
	private void initiateButtons(){
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logicControl.buttonPressed(LogicControl.BUTTON_CONNECT_BACK);
			}
		});
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logicControl.buttonPressed(LogicControl.BUTTON_CONNECT_CONNECT);
			}
		});
		
	}
	
	public String getIp(){
		return ipField.getText();
	}
	
	public int getPort(){
		return Integer.valueOf(portField.getText());
	}

}
