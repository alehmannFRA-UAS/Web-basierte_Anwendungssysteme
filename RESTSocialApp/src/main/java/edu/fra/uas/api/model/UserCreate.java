package edu.fra.uas.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import edu.fra.uas.security.model.Role;

public class UserCreate {

    @NotEmpty(message = "Nickname is required.")
    private String nickname = "";

    @NotEmpty(message = "Email address is required.")
    @Email(message = "The email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
    private String email = "";

    @NotEmpty(message="Password is a required.")
    @Size(min = 8, message = "Password must be equal to or greater than 8 characters.")
    private String password = "";

    @NotEmpty(message="Repeated password is a required.")
    @Size(min = 8, message = "Password must be equal to or greater than 8 characters.")
    private String passwordRepeated = "";

    private Role role = Role.USER;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
