package edu.fra.uas.security.service.user;

import java.util.Collection;
import java.util.Optional;

import edu.fra.uas.security.model.User;
import edu.fra.uas.security.model.UserCreateForm;
import edu.fra.uas.security.service.dto.UserDTO;

public interface UserService {

	UserDTO getUserById(long id);

	Optional<User> getUserByEmail(String email);
    
	boolean existsByNickname(String nickname);
    
	boolean existsByEmail(String email);
    
	Collection<UserDTO> getAllUsers();
    
	User create(UserCreateForm form);

}
