package edu.fra.uas.message.service;

import java.util.List;

import edu.fra.uas.message.model.Message;

public interface MessageService {
	
    List<Message> listAllMessagesFromTo(String from, String to);

    void addMessage(String from, String to, String content);
    
}
