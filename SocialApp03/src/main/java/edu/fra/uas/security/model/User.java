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
	
	@Column(name = "password", nullable = false)
	private String password;
	
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", password='" + password.substring(0, 10) +
                ", role=" + role +
                '}';
    }
	
}
