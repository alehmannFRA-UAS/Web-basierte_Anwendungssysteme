package edu.fra.uas.conversation.service.dto;

public class ConversationDTO {

	private String conversationWith;
	
	private int newMessages;

	public String getConversationWith() {
		return conversationWith;
	}

	public void setConversationWith(String conversationWith) {
		this.conversationWith = conversationWith;
	}

	public int getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(int newMessages) {
		this.newMessages = newMessages;
	}
		
}
