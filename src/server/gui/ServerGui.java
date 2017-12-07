package server.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.ClientThread;
import server.Server;

import main.gui.CardPanel;

public class ServerGui {
	
	public final static Dimension SIZE = new Dimension(200, 300);
	
	private Server server;
	private JFrame frame;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private Box contentBox;
	
	public ServerGui(Server server) {
		frame = new JFrame("Server");
		contentPanel = new CardPanel();
		scrollPane = new JScrollPane(contentPanel);
		frame.setContentPane(scrollPane);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		contentBox = new Box(BoxLayout.PAGE_AXIS);
		contentPanel.add(contentBox);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				ServerGui.this.server.buttonPressed(Server.BUTTON_SHUT_DOWN);
			}
		});
		frame.setSize(SIZE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void updateClientThreads(List<ClientThread> clients){
		contentBox.removeAll();
		contentBox.revalidate();
		for (ClientThread ct : clients){
			JButton button = new JButton(ct.getPlayer().getName());
			contentBox.add(button);
			contentBox.add(Box.createRigidArea(new Dimension(0, 5)));
		}
		
	}
	
	public JPanel getPanel(){
		return contentPanel;
	}

}
