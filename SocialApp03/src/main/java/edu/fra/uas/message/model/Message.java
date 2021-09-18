package edu.fra.uas.message.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import edu.fra.uas.common.BaseEntity;
import edu.fra.uas.conversation.model.Conversation;

@Entity
@EntityListeners(MessageListener.class)
public class Message extends BaseEntity<Long>{

    private String content;

    private String timestamp;

    private String direction;
    
    @ManyToOne
    private Conversation conversation;

    public Message() {
        this.content = "";
        this.timestamp = "";
        this.direction = "";
        this.conversation = new Conversation();
    }

    public Message(String content, String timestamp, String direction) {
        this.content = content;
        this.timestamp = timestamp;
        this.direction = direction;
    }
    
    public Message(String content, Conversation conversation, String direction) {
        this.content = content;
        this.conversation = conversation;
        this.direction = direction;
    }    

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDirection() {
        return direction;
    }
}
