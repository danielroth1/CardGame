package main.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.logic.LogicControl;

public class GuiControl {
	
	public final static Dimension FRAME_SIZE = new Dimension(600, 600);
	public final static Color BACKGROUND_COLOR_BRIGHT = new Color(0, 125, 63);
	public final static Color BACKGROUND_COLOR_DARK = new Color(0, 94, 47);
	public final static int PANEL_START = 0;
	public final static int PANEL_CONNECT = 1;
	public final static int PANEL_HOST = 2;
	public final static int PANEL_CLIENT = 3;
	
	
	private LogicControl logicControl;
	private JFrame frame;
	private JPanel contentPanel;
	private StartPanel startPanel;
	private ConnectPanel connectPanel;
	private HostPanel hostPanel;
	private JPanel clientPanel = null;
	
	public GuiControl() {
		
	}
	
	public void initiateComponents(LogicControl logicControl){
		this.logicControl = logicControl;
		start();
	}
	
	private void start(){
		frame = new JFrame("Card Game");
		contentPanel = new CardPanel();
		frame.setContentPane(contentPanel);
		
		initiatePanels();
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		contentPanel.add(hostPanel);
		contentPanel.add(startPanel);
		contentPanel.add(connectPanel);
		
		setPanelVisible(PANEL_START);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		frame.setSize(FRAME_SIZE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
	}
	
	private void initiatePanels(){
		startPanel = new StartPanel(logicControl);
		connectPanel = new ConnectPanel(logicControl);
		hostPanel = new HostPanel(logicControl);
	}
	
	public void setPanelVisible(int panel){
		switch(panel){
		case PANEL_START:
			hideAll();
			startPanel.setVisible(true);
			break;
		case PANEL_CONNECT:
			hideAll();
			connectPanel.setVisible(true);
			break;
		case PANEL_HOST:
			hideAll();
			hostPanel.setVisible(true);
			break;
		case PANEL_CLIENT:
			if (clientPanel != null){
				hideAll();
				clientPanel.setVisible(true);
			}
			break;
		}
	}
	
	public void setClientPanel(JPanel clientPanel){
		if (clientPanel != null)
			contentPanel.remove(clientPanel);
		this.clientPanel = clientPanel;
		contentPanel.add(clientPanel);
		contentPanel.revalidate();
	}
	
	private void hideAll(){
		startPanel.setVisible(false);
		connectPanel.setVisible(false);
		hostPanel.setVisible(false);
		if (clientPanel != null)
			clientPanel.setVisible(false);
	}
	
	public int getPortConnect(){
		return connectPanel.getPort();
	}
	
	public String getIpConnect(){
		return connectPanel.getIp();
	}
	
	public int getPortHost(){
		return hostPanel.getPort();
	}

}
