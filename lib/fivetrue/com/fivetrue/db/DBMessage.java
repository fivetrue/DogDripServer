package com.fivetrue.db;

public class DBMessage {
	
	private int row = 0;
	String message = null;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "DBMessage [row=" + row + ", message=" + message + "]";
	}
}
