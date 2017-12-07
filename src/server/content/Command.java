package server.content;

import java.io.Serializable;

public class Command implements Serializable{
	
	private int message;
	private Object obj1;
	private Object obj2;
	
	public Command(int message) {
		this.message = message;
	}

	public int getMessage() {
		return message;
	}

	public Object getObj1() {
		return obj1;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}
	
	

}
