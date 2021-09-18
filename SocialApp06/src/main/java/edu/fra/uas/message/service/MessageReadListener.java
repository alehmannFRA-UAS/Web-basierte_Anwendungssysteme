package edu.fra.uas.message.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.service.ConversationUserService;
import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.repository.MessageRepository;

@Component
public class MessageReadListener { 
	
	private static final Logger log = LoggerFactory.getLogger(MessageReadListener.class);
	
	private MessageRepository messageRepository;
    private ConversationUserService conversationUserService;
    
    @Autowired
    public MessageReadListener(MessageRepository messageRepository, ConversationUserService conversationUserService) {
		this.messageRepository = messageRepository;
		this.conversationUserService = conversationUserService;
	}

	@EventListener
    public void handleMessageReadEvent(MessageReadEvent event) {
		String from= event.getFrom();
		String to = event.getTo();
		
		ConversationUser chatuserFrom = conversationUserService.getByNickname(to);
        List<Message> targetListOrigin = new ArrayList<>(chatuserFrom.getConversations().get(from).getMessages()); 
		targetListOrigin.forEach(message -> {
        	if (message.getDirection().equals("out") && message.getRead().equals("false")) {
        		log.debug("message read event received with " + from + " --> " + to);
        		message.setRead("true");
        		messageRepository.save(message);
        	}
        });
	}
	
}