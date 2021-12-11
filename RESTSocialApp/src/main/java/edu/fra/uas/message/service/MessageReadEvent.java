package edu.fra.uas.message.service;

public class MessageReadEvent {

	private String from;
    private String to;
    
	public MessageReadEvent(String to, String from) {
		this.to = to;
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	@Override
	public String toString() {
		return "MessageReadEvent [to=" + to + "from=" + from + "]";
	}
	
}