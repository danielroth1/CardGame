package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.content.Card;
import server.content.Player;

public class ServerThread {
	
	private Client client;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Thread threadIn;
	private boolean running = true;
	
	
	public ServerThread(Client client, Socket socket) {
		this.client = client;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("error while initializing object in- and outputstream in client");
			e.printStackTrace();
		}
		
		threadIn = new Thread(){
			public void run(){
				while(running){
					try {
						Object o = in.readObject();
						ServerThread.this.client.receivedObject(o);
					} catch (ClassNotFoundException | IOException e) {
						System.out.println("error while reading object in client");
						e.printStackTrace();
					}
					
					
				}
			}
		};
		try {
			out.writeObject(client.getPlayer());
			out.reset();
		} catch (IOException e) {
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
			out.flush();
		} catch (IOException e) {
			System.out.println("error while sending an object from server to client");
			e.printStackTrace();
		}
	}
	
	
	

}
