package edu.fra.uas.conversation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.fra.uas.message.model.Message;
import edu.fra.uas.user.model.User;

@Entity
public class Conversation {

    @Id
    @GeneratedValue
    private Integer id;

    private String conversationWith;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    private int newMessages;

    public Conversation() {}

    public Conversation(String conversationWith, User user) {
        this.messages = new ArrayList<>();
        this.conversationWith = conversationWith;
        this.user = user;
    }

    public Integer getId() {
        return id;
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

    public User getUser() {
        return user;
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
        return "Conversation{ id=" + id + " conversationWith=" + conversationWith + " owner=" + user.getName() + "}";
    }

}
