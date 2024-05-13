package model;

import java.sql.Timestamp;

public class Message {

	private int id;
	private String nick;
	private String message;
	private Timestamp ts;
	
	public Message(int id, String nick, String message, Timestamp ts) {
		this.id = id;
		this.nick = nick;
		this.message = message;
		this.ts = ts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", nick=" + nick + ", message=" + message + ", ts=" + ts + "]";
	}

}
