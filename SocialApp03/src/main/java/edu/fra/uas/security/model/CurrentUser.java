package edu.fra.uas.security.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUser implements Serializable {

    private User user;

    private String email;

    private String password;

    public CurrentUser() {}
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getNickname() {
        return user.getNickname();}

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "getNickname=" + user.getNickname() +
                "} " + super.toString();
    }

}
