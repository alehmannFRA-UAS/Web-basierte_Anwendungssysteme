package edu.fra.uas.conversation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.fra.uas.common.BaseEntity;
import edu.fra.uas.message.model.Message;

@Entity
public class Conversation extends BaseEntity<Long> {

    private String conversationWith;

    @ManyToOne
    private ConversationUser conversationUser;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Message> messages;

    private int newMessages;

    public Conversation() {}
    
    public Conversation(String conversationWith) {
        this.messages = new ArrayList<>();
        this.conversationWith = conversationWith;
    }

    public Conversation(String conversationWith, ConversationUser conversationUser) {
        this.messages = new ArrayList<>();
        this.conversationWith = conversationWith;
        this.conversationUser = conversationUser;
    }

    public String getConversationWith() {
        return conversationWith;
    }

    public void setConversationWith(String conversationWith) {
        this.conversationWith = conversationWith;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessages(Message message) {
        this.messages.add(message);
    }

    public ConversationUser getConversationUser() {
        return conversationUser;
    }

    public int getNewMessages() {
        return newMessages;
    }

    public void addNewMessages() {
        this.newMessages++;
    }

    public void resetNewMessages() {
        this.newMessages = 0;
    }

    @Override
    public String toString() {
        return "Conversation{ id=" + getId() + " conversationWith=" + conversationWith + " owner=" + conversationUser.getNickname() + "}";
    }

}
