package client.gui.content;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.gui.GuiControl;

public class ChatPanel extends JPanel{
	
	private JTextPane readField = new JTextPane();
	private JTextField writeField = new JTextField();
	private JScrollPane scrollPane;
	
	public ChatPanel() {
		setBackground(GuiControl.BACKGROUND_COLOR_BRIGHT);
		setForeground(Color.WHITE);
		readField.setEditable(false);
		readField.setBackground(GuiControl.BACKGROUND_COLOR_DARK);
		readField.setForeground(Color.WHITE);
		writeField.setBackground(GuiControl.BACKGROUND_COLOR_DARK);
		writeField.setForeground(Color.WHITE);
		scrollPane = new JScrollPane(readField);
		writeField.setMinimumSize(new Dimension(0, 40));
		writeField.setPreferredSize(new Dimension(1000, 40));
		writeField.setMaximumSize(new Dimension(1000, 40));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(scrollPane);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(writeField);
		
		
	}
	
	public void addText(String text){
		try {
			Document doc = readField.getStyledDocument();
			doc.insertString(doc.getLength(), text + "\n", null);
			JScrollBar scroll = scrollPane.getVerticalScrollBar();
			scroll.setValue(scroll.getMaximum());
		} catch (BadLocationException e) {
			System.out.println("error while trying to append string in chat panel");
			e.printStackTrace();
		}
	}
	
	public String getWrittenTextAndClear(){
		String s = writeField.getText();
		writeField.setText("");
		return s;
	}
	
	public void addKeyListener(KeyListener kl){
		writeField.addKeyListener(kl);
	}

}
