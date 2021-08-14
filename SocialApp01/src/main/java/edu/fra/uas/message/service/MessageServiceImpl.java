package edu.fra.uas.message.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.service.ConversationService;
import edu.fra.uas.message.model.Message;
import edu.fra.uas.user.model.User;
import edu.fra.uas.user.service.UserService;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    private UserService userService;
    private ConversationService conversationService;

    @Autowired
    public MessageServiceImpl(UserService userService, ConversationService conversationService) {
    	log.debug("MessageService instantiated");
        this.userService = userService;
        this.conversationService = conversationService;
    }

    @Override
    public List<Message> listAllMessagesFromTo(String from, String to) {
    	log.debug("list all messages from " + from + " to " + to);
        User userFrom = userService.getByName(from);
        conversationService.resetNewMessages(from, to);
        return userFrom.getConversations().get(to).getMessages();
    }

    @Override
    public void addMessage(String from, String to, String content) {
    	log.debug("add message from " + from + " to " + to + " with content: " + content);
        Conversation conversationFrom =  userService.getConversationFromByNameTo(to,from);
        Conversation conversationTo =  userService.getConversationFromByNameTo(from,to);
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
