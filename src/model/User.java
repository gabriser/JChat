package model;

import java.sql.Timestamp;

public class User {

	private String nick;
    private String userhost;
    private Timestamp dateCon;
    private int lastRead;
    
	public User(String nick, String userhost, Timestamp dateCon, int lastRead) {
		this.nick = nick;
		this.userhost = userhost;
		this.dateCon = dateCon;
		this.lastRead = lastRead;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUserhost() {
		return userhost;
	}

	public void setUserhost(String userhost) {
		this.userhost = userhost;
	}

	public Timestamp getDateCon() {
		return dateCon;
	}

	public void setDateCon(Timestamp dateCon) {
		this.dateCon = dateCon;
	}

	public int getLastRead() {
		return lastRead;
	}

	public void setLastRead(int lastRead) {
		this.lastRead = lastRead;
	}

	@Override
	public String toString() {
		return "User [nick=" + nick + ", userhost=" + userhost + ", dateCon=" + dateCon + ", lastRead=" + lastRead
				+ "]";
	}

}
