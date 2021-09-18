package edu.fra.uas.message.service;

import java.util.List;

import edu.fra.uas.message.service.dto.MessageDTO;

public interface MessageService {
	
    List<MessageDTO> listAllMessagesFromTo(String from, String to);

    void addMessage(String from, String to, String content);
    
}
