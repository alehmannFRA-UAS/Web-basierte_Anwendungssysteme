package edu.fra.uas.api.model;

import org.springframework.hateoas.RepresentationModel;

public class Message extends RepresentationModel<Message> {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
