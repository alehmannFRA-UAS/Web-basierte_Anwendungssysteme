package edu.fra.uas.conversation.service;

import java.util.List;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.service.dto.ConversationDTO;
import edu.fra.uas.security.model.UserCreateForm;

public interface ConversationUserService {

    ConversationUser getByNickname(String nickname);

    boolean existsNickname(String to);

    boolean conversationUserConversationsContainsKeyfindByNickname(String from, String to);

    void newConversationFromTo(String from, String to);

    void deleteConversationFromTo(String from, String to);

    Conversation getConversationFromByNicknameTo(String from, String to);
    
    void createConversationsUser(UserCreateForm form);
    
    List<ConversationDTO> getAllConversationsFrom(String from);
    
}
