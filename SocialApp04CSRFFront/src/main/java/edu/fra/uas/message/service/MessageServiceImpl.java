package edu.fra.uas.message.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.service.ConversationService;
import edu.fra.uas.conversation.service.ConversationUserService;
import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.service.dto.MessageDTO;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private ConversationUserService conversationUserService;
    private ConversationService conversationService;

    @Autowired
    public MessageServiceImpl(ConversationUserService conversationUserService, ConversationService conversationService) {
    	log.debug("MessageService instantiated");
        this.conversationUserService = conversationUserService;
        this.conversationService = conversationService;
    }

    @Override
    public List<MessageDTO> listAllMessagesFromTo(String from, String to) {
    	log.debug("list all messages from " + from + " to " + to);
        ConversationUser userFrom = conversationUserService.getByNickname(from);
        List<Message> targetListOrigin = new ArrayList<>(userFrom.getConversations().get(to).getMessages()); 
		List<MessageDTO> targetList= new ArrayList<MessageDTO>(); 
		for (Message source: targetListOrigin ) {
			MessageDTO target= new MessageDTO();
	        BeanUtils.copyProperties(source , target);    
	        targetList.add(target);
	    };
        conversationService.resetNewMessages(from, to);
        return targetList;
    }

    @Override
    public void addMessage(String from, String to, String content) {
    	log.debug("add message from " + from + " to " + to + " with content: " + content);
        Conversation conversationFrom =  conversationUserService.getConversationFromByNicknameTo(to,from);
        Conversation conversationTo =  conversationUserService.getConversationFromByNicknameTo(from,to);
        if (conversationTo != null) {
            conversationService.saveMessages(conversationFrom, content, "out");
            conversationTo.addNewMessages();
            conversationService.saveMessages(conversationTo, content, "in");
        } else {
        	conversationService.saveMessages(conversationFrom, content, "out");
        	conversationService.saveMessages(conversationFrom, "conversation with " + to + " deleted", "in");
        }
    }

}
