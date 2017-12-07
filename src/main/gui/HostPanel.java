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

public class HostPanel extends JPanel{
	
	private LogicControl logicControl;
	private JLabel portLabel = new JLabel("Port: ");
	private JTextField portField = new JTextField();
	private JButton backButton = new JButton("Back");
	private JButton createServerButton = new JButton("Create Server");
	
	
	public HostPanel(LogicControl logicControl) {
		this.logicControl = logicControl;
		setOpaque(false);
		initiateButtons();
		portLabel.setForeground(Color.WHITE);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		portField.setMaximumSize(new Dimension(50, 25));
		Box contentBox = new Box(BoxLayout.PAGE_AXIS);
		Box portBox = new Box(BoxLayout.LINE_AXIS);
		portBox.add(portLabel);
		portBox.add(Box.createRigidArea(new Dimension(5, 0)));
		portBox.add(portField);
		contentBox.add(portBox);
		contentBox.add(Box.createRigidArea(new Dimension(0, 5)));
		
		Box buttonBox = new Box(BoxLayout.LINE_AXIS);
		buttonBox.add(backButton);
		buttonBox.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonBox.add(createServerButton);
		contentBox.add(buttonBox);
		
		add(Box.createVerticalGlue());
		add(contentBox);
		add(Box.createVerticalGlue());
		
	}
	
	private void initiateButtons(){
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logicControl.buttonPressed(LogicControl.BUTTON_HOST_BACK);
			}
		});
		createServerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logicControl.buttonPressed(LogicControl.BUTTON_HOST_START);
			}
		});
		
	}
	
	public int getPort(){
		return Integer.valueOf(portField.getText());
	}

}
