package main;

import main.gui.GuiControl;
import main.logic.LogicControl;

public class Main {
	
	private LogicControl logicControl;
	private GuiControl guiControl;
	
	public Main() {
		logicControl = new LogicControl();
		guiControl = new GuiControl();
		
		logicControl.initiateComponents(guiControl);
		guiControl.initiateComponents(logicControl);
	}
	
	public static void main(String[] args){
		new Main();
	}

}
