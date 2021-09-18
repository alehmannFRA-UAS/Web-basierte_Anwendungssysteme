package edu.fra.uas.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import edu.fra.uas.security.model.Role;
import edu.fra.uas.security.model.User;
import edu.fra.uas.security.repository.UserRepository;

@Component
public class InitializeDB {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitializeDB.class);

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init()  {

        LOGGER.debug("Db initialized");

        User user = new User();
        user.setNickname("admin");
        user.setEmail("admin@example.com");
        user.setPasswordHash(new BCryptPasswordEncoder().encode("admin"));        
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        user = new User();
        user.setNickname("bob");
        user.setEmail("bob@example.com");
        user.setPasswordHash(new BCryptPasswordEncoder().encode("bob"));
        user.setRole(Role.USER);
        userRepository.save(user);

        user = new User();
        user.setNickname("alice");
        user.setEmail("alice@example.com");
        user.setPasswordHash(new BCryptPasswordEncoder().encode("alice"));
        user.setRole(Role.USER);
        userRepository.save(user);

    }
    
}
