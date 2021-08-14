package edu.fra.uas.user.service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.user.model.User;

public interface UserService {

    User getByName(String name);

    Object existsName(String to);

    boolean userConversationsContainsKeyfindByName(String from, String to);

    void newConversationFromTo(String from, String to);

    void deleteConversationFromTo(String from, String to);

    Conversation getConversationFromByNameTo(String from, String to);
    
}
