package edu.fra.uas.message.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message {

    @Id 
    @GeneratedValue
    private Integer id;

    private String content;

    private String timestamp;

    private String direction;

    public Message() {
        this.content = "";
        this.timestamp = "";
        this.direction = "";
    }

    public Message(String content, String timestamp, String direction) {
        this.content = content;
        this.timestamp = timestamp;
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
