package edu.fra.uas.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import edu.fra.uas.common.BaseEntity;

@Entity
@Table(name = "secuser")
public class User extends BaseEntity<Long>{

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	@Column(name = "role", nullable = false)
	private Role role;
	
	public Long getId() {		
		return super.getId();
	}
	
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                "nicknameo=" + nickname +
                ", email='" + email.replaceFirst("@.*", "@***") +
                ", password='" + passwordHash.substring(0, 10) +
                ", role=" + role +
                '}';
    }
	
}
