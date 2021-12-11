package edu.fra.uas.conversation.service.dto;

import org.springframework.hateoas.RepresentationModel;

public class ConversationDTO extends RepresentationModel<ConversationDTO> {

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
