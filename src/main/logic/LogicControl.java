package main.logic;

import server.Server;
import client.Client;
import main.gui.GuiControl;

public class LogicControl {
	
	public final static int BUTTON_CONNECT_BACK = 0;
	public final static int BUTTON_CONNECT_CONNECT = 1;
	public final static int BUTTON_START_JOIN = 2;
	public final static int BUTTON_START_HOST = 3;
	public final static int BUTTON_START_EXIT = 4;
	public final static int BUTTON_HOST_START = 5;
	public final static int BUTTON_HOST_BACK = 6;
	
	
	private GuiControl guiControl;
	
	public LogicControl() {
		
	}
	
	public void initiateComponents(GuiControl guiControl){
		this.guiControl = guiControl;
		start();
	}
	
	private void start(){
		
	}
	
	public void buttonPressed(int button){
		switch(button){
		case BUTTON_CONNECT_CONNECT:
			Client c = new Client("Player1", guiControl.getIpConnect(), guiControl.getPortConnect());
			guiControl.setClientPanel(c.getPanel());
			guiControl.setPanelVisible(GuiControl.PANEL_CLIENT);
			break;
		case BUTTON_CONNECT_BACK:
			guiControl.setPanelVisible(GuiControl.PANEL_START);
			break;
		case BUTTON_START_JOIN:
			guiControl.setPanelVisible(GuiControl.PANEL_CONNECT);
			break;
		case BUTTON_START_HOST:
			guiControl.setPanelVisible(GuiControl.PANEL_HOST);
			break;
		case BUTTON_START_EXIT:
			System.exit(0);
			break;
		case BUTTON_HOST_START:
			new Server(guiControl.getPortHost());
			break;
		case BUTTON_HOST_BACK:
			guiControl.setPanelVisible(GuiControl.PANEL_START);
			
			break;
		}
	}
}
