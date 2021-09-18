package edu.fra.uas.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.repository.ConversationRepository;
import edu.fra.uas.conversation.repository.ConversationUserRepository;
import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.repository.MessageRepository;
import edu.fra.uas.security.model.Role;
import edu.fra.uas.security.model.User;
import edu.fra.uas.security.repository.UserRepository;

@Component
public class InitializeDB {

    private static final Logger log = LoggerFactory.getLogger(InitializeDB.class);

    @Autowired
    ConversationUserRepository conversationUserRepository;
    
    @Autowired
    ConversationRepository conversationRepository;
    
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @PostConstruct
    public void init()  {
    	log.debug(" >>> init database");
    	
    	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	String passwordString;
            
        User user = new User();
        user.setNickname("admin");
        user.setEmail("admin@example.com");
        passwordString = passwordEncoder.encode("admin");
        user.setPasswordHash(passwordString);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        
        log.info("Password Hash --> password is 'admin' : " + passwordString);
        log.info("Password Hash --> hash is : " + user.getPasswordHash());

        user = new User();
        user.setNickname("bob");
        user.setEmail("bob@example.com");
        user.setPasswordHash(passwordEncoder.encode("bob"));
        user.setRole(Role.USER);
        userRepository.save(user);

        user = new User();
        user.setNickname("alice");
        user.setEmail("alice@example.com");
        user.setPasswordHash(passwordEncoder.encode("alice"));
        user.setRole(Role.USER);
        userRepository.save(user);

        ConversationUser user1 = new ConversationUser();
        user1.setNickname("admin");
        ConversationUser user2 = new ConversationUser();
        user2.setNickname("bob");
        ConversationUser user3 = new ConversationUser();
        user3.setNickname("alice");
        
        conversationUserRepository.save(user1);
        conversationUserRepository.save(user2);        
        conversationUserRepository.save(user3);

        Conversation conversation1 = new Conversation(user2.getNickname(), user3);
        Conversation conversation2 = new Conversation(user3.getNickname(), user2);
 
        user2.addConversation(conversation1);
        user3.addConversation(conversation2);
        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);

        Message message1 = new Message("Hello Bob", conversation1, "out");
        messageRepository.save(message1);
        Message message2 = new Message("Hello Bob", conversation2, "in");
        messageRepository.save(message2);
        conversation1.addMessages(message1);
        conversation2.addMessages(message2);

        message1 = new Message("Hi Alice", conversation1, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Alice", conversation2, "out");
        messageRepository.save(message2);
        conversation1.addMessages(message1);
        conversation2.addMessages(message2);

    }
}
