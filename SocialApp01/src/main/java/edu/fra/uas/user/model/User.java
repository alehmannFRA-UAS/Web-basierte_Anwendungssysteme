package edu.fra.uas.user.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fra.uas.conversation.model.Conversation;

@Entity
public class User implements Serializable {

    @Transient
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Id 
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @MapKey(name= "conversationWith")
    private Map<String, Conversation> conversations = new HashMap<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, Conversation> conversations) {
        this.conversations = conversations;
    }

    public boolean addConversation(Conversation conversation) {
        if (this.conversations == null) {
            this.conversations = new HashMap<>(); return true;
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
        return "User{ id=" + id + " name=" + name + ", conversations='" + conversations + "}";
    }

}
