package edu.fra.uas.security.service.user;

import java.util.Collection;

import edu.fra.uas.security.model.User;

public interface UserService {

	User getUserById(long id);

	User getUserByEmail(String email);
    
	boolean existsByNickname(String nickname);
    
	boolean existsByEmail(String email);
    
	Collection<User> getAllUsers();

}
