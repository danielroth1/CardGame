package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import server.content.Command;
import server.content.Player;

public class ClientThread {
	
	private Server server;
	private Player player;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Thread threadIn;
	private boolean running = true;
	/**
	 * commands that are going to be sent out
	 */
	private List<Command> commandsOut = new LinkedList<Command>();
	
	public ClientThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("error on initializing out and input stream");
			e.printStackTrace();
		}
		
		threadIn = new Thread(){
			public void run(){
				while(running){
					try {
						Object o = in.readObject();
						ClientThread.this.server.objectReceived(ClientThread.this, o);
					} catch (ClassNotFoundException | IOException e) {
						System.out.println("error while receiving object from client");
						e.printStackTrace();
					}
				}
			}
		};
		try {
			Object o;
			o = in.readObject();
			if (o instanceof Player){
				this.player = (Player)o;
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("error while receiving player object");
			e.printStackTrace();
		}
		threadIn.start();
	}
	
	public void stop(){
		running = false;
	}
	
	public void writeObject(Object obj){
		try {
			out.writeObject(obj);
			out.reset();
		} catch (IOException e) {
			System.out.println("error while sending an object from server to client");
			e.printStackTrace();
		}
	}
	
	public Player getPlayer(){
		return player;
	}

	public List<Command> getCommandsOut() {
		return commandsOut;
	}
	
	
}
