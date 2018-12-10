package tw.com.ctt.model;

import java.io.Serializable;

public class WriteInfo implements Serializable {
	private static final long serialVersionUID = -2730344194520081819L;
	private int weServerIndex;
	private String command;
	private String message;
	private String receiver;

	public int getWeServerIndex() {
		return weServerIndex;
	}

	public void setWeServerIndex(int weServerIndex) {
		this.weServerIndex = weServerIndex;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

}
