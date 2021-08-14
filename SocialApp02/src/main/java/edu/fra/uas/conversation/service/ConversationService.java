package edu.fra.uas.conversation.service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.user.model.User;

public interface ConversationService {

    void newConversationBetween(User userTo, User userFrom);

    void deleteConversationBetween(User userFrom, User userTo);

    void saveMessages(Conversation conversation, String content, String direction);

    void resetNewMessages(String from, String to);
    
}
