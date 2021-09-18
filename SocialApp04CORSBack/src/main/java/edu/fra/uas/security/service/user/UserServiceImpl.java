package edu.fra.uas.security.service.user;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.security.model.User;
import edu.fra.uas.security.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id) {
        log.debug("getting user=", id);
        User user = userRepository.findById(id).get();
        return user;
    }
    
    @Override
    public User getUserByEmail(String email) {
        log.debug("getting user by email=" + email);        
        return userRepository.findOneByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        log.debug("getting all users");
        Collection<User> targetList = userRepository.findAllByOrderByNicknameAsc();
        return targetList;
    }

}
