package edu.fra.uas.conversation.service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;

public interface ConversationService {

    void newConversationBetween(ConversationUser userTo, ConversationUser userFrom);

    boolean deleteConversationBetween(ConversationUser userFrom, ConversationUser userTo);

    void saveMessages(Conversation conversation, String content, String direction);

    void resetNewMessages(String from, String to);
    
}
