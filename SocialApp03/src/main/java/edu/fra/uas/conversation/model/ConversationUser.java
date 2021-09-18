package edu.fra.uas.conversation.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import edu.fra.uas.common.BaseEntity;

@Entity
public class ConversationUser extends BaseEntity<Long> {

    private String nickname;
    
    private ConversationType conversationType = ConversationType.Normal;

    @OneToMany(mappedBy = "conversationUser", cascade = CascadeType.ALL)
    @MapKey(name= "conversationWith")
    private Map<String, Conversation> conversations = new HashMap<>();

    public ConversationUser() {}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public ConversationType getConversationType() {
		return conversationType;
	}
    
    public void setConversationType(ConversationType conversationType) {
		this.conversationType = conversationType;
	}

    public Map<String, Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, Conversation> conversations) {
        this.conversations = conversations;
    }

    public boolean addConversation(Conversation conversation) {
        if (this.conversations == null) {
            this.conversations = new HashMap<>(); 
            return true;
        }
        else {
            if (!this.conversations.containsKey(conversation.getConversationWith())) {
                this.conversations.put(conversation.getConversationWith(), conversation);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ConversationUser{ id=" + getId() + " nickname=" + nickname + ", conversations='" + conversations + "}";
    }

}
