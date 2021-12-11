package edu.fra.uas.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationType;
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
        
        user = new User();
        user.setNickname("celine");
        user.setEmail("celine@example.com");
        user.setPasswordHash(passwordEncoder.encode("celine"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        user = new User();
        user.setNickname("dan");
        user.setEmail("dan@example.com");
        user.setPasswordHash(passwordEncoder.encode("dan"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        user = new User();
        user.setNickname("eve");
        user.setEmail("eve@example.com");
        user.setPasswordHash(passwordEncoder.encode("eve"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        user = new User();
        user.setNickname("frank");
        user.setEmail("frank");
        user.setPasswordHash(passwordEncoder.encode("frank"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        user = new User();
        user.setNickname("grace");
        user.setEmail("grace");
        user.setPasswordHash(passwordEncoder.encode("grace"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        user = new User();
        user.setNickname("heidi");
        user.setEmail("heidi");
        user.setPasswordHash(passwordEncoder.encode("heidi"));
        user.setRole(Role.USER);
        userRepository.save(user);
        
        ConversationUser userAdmin = new ConversationUser();
        userAdmin.setNickname("admin");
        ConversationUser userBob = new ConversationUser();
        userBob.setNickname("bob");
        ConversationUser userAlice = new ConversationUser();
        userAlice.setNickname("alice");        
        ConversationUser userCeline = new ConversationUser();
        userCeline.setNickname("celine");
        ConversationUser userDan = new ConversationUser();
        userDan.setNickname("dan");
        ConversationUser userEve = new ConversationUser();
        userEve.setNickname("eve");
        ConversationUser userFrank = new ConversationUser();
        userFrank.setNickname("frank");
        ConversationUser userGrace = new ConversationUser();
        userGrace.setNickname("grace");
        ConversationUser userHeidi = new ConversationUser();
        userHeidi.setNickname("heidi");
        
        conversationUserRepository.save(userAdmin);
        conversationUserRepository.save(userBob);        
        conversationUserRepository.save(userAlice);
        conversationUserRepository.save(userCeline);
        conversationUserRepository.save(userDan);
        conversationUserRepository.save(userEve);
        conversationUserRepository.save(userFrank);
        conversationUserRepository.save(userGrace);
        conversationUserRepository.save(userHeidi);
        
        Conversation conversationBob2Alice = new Conversation(userBob.getNickname(), userAlice);
        Conversation conversationAlice2Bob = new Conversation(userAlice.getNickname(), userBob);        
        Conversation conversationBob2Celine = new Conversation(userBob.getNickname(), userCeline);
        Conversation conversationCeline2Bob = new Conversation(userCeline.getNickname(), userBob);        
        Conversation conversationBob2Dan = new Conversation(userBob.getNickname(), userDan);
        Conversation conversationDan2Bob = new Conversation(userDan.getNickname(), userBob);        
        Conversation conversationBob2Eve = new Conversation(userBob.getNickname(), userEve);
        Conversation conversationEve2Bob = new Conversation(userEve.getNickname(), userBob);        
        Conversation conversationBob2Frank = new Conversation(userBob.getNickname(), userFrank);
        Conversation conversationFrank2Bob = new Conversation(userFrank.getNickname(), userBob);        
        Conversation conversationBob2Grace= new Conversation(userBob.getNickname(), userGrace);
        Conversation conversationGrace2Bob = new Conversation(userGrace.getNickname(), userBob);        
        Conversation conversationBob2Heidi = new Conversation(userBob.getNickname(), userHeidi);
        Conversation conversationHeidi2Bob = new Conversation(userHeidi.getNickname(), userBob);
        
        userBob.addConversation(conversationBob2Alice);
        userAlice.addConversation(conversationAlice2Bob);        
        userBob.addConversation(conversationBob2Celine);
        userCeline.addConversation(conversationCeline2Bob);
        userBob.addConversation(conversationBob2Dan);
        userDan.addConversation(conversationDan2Bob);
        userBob.addConversation(conversationBob2Eve);
        userEve.addConversation(conversationEve2Bob);
        userBob.addConversation(conversationBob2Frank);
        userFrank.addConversation(conversationFrank2Bob);
        userBob.addConversation(conversationBob2Grace);
        userGrace.addConversation(conversationGrace2Bob);
        userBob.addConversation(conversationBob2Heidi);
        userHeidi.addConversation(conversationHeidi2Bob);
                
        conversationRepository.save(conversationBob2Alice);
        conversationRepository.save(conversationAlice2Bob);
        conversationRepository.save(conversationBob2Celine);
        conversationRepository.save(conversationCeline2Bob);
        conversationRepository.save(conversationBob2Dan);
        conversationRepository.save(conversationDan2Bob);
        conversationRepository.save(conversationBob2Eve);
        conversationRepository.save(conversationEve2Bob);        
        conversationRepository.save(conversationBob2Frank);
        conversationRepository.save(conversationFrank2Bob);
        conversationRepository.save(conversationBob2Grace);
        conversationRepository.save(conversationGrace2Bob);
        conversationRepository.save(conversationBob2Heidi);
        conversationRepository.save(conversationHeidi2Bob);
        
        Message message1;Message message2;

        message1 = new Message("Hello Bob I am Alice", conversationBob2Alice, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Alice", conversationAlice2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Alice.addMessages(message1);
        conversationAlice2Bob.addMessages(message2);
        
        message1 = new Message("Hi Alice I am Bob", conversationBob2Alice, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Alice I am Bob", conversationAlice2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Alice.addMessages(message1);
        conversationAlice2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Celine", conversationBob2Celine, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Celine", conversationCeline2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Celine.addMessages(message1);
        conversationCeline2Bob.addMessages(message2);
        
        message1 = new Message("Hi Celine I am Bob", conversationBob2Celine, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Celine I am Bob", conversationCeline2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Celine.addMessages(message1);
        conversationCeline2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Dan", conversationBob2Dan, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Dan", conversationDan2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Dan.addMessages(message1);
        conversationDan2Bob.addMessages(message2);
        
        message1 = new Message("Hi Dan I am Bob", conversationBob2Dan, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Dan I am Bob", conversationDan2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Dan.addMessages(message1);
        conversationDan2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Eve", conversationBob2Eve, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Eve", conversationEve2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Eve.addMessages(message1);
        conversationEve2Bob.addMessages(message2);
        
        message1 = new Message("Hi Eve I am Bob", conversationBob2Eve, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Eve I am Bob", conversationEve2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Eve.addMessages(message1);
        conversationEve2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Frank", conversationBob2Frank, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Frank", conversationFrank2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Frank.addMessages(message1);
        conversationFrank2Bob.addMessages(message2);
        
        message1 = new Message("Hi Frank I am Bob", conversationBob2Frank, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Frank I am Bob", conversationFrank2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Frank.addMessages(message1);
        conversationFrank2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Grace", conversationBob2Grace, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Grace", conversationGrace2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Grace.addMessages(message1);
        conversationGrace2Bob.addMessages(message2);
        
        message1 = new Message("Hi Grace I am Bob", conversationBob2Grace, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Grace I am Bob", conversationGrace2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Grace.addMessages(message1);
        conversationGrace2Bob.addMessages(message2);
        
        message1 = new Message("Hello Bob I am Heidi", conversationBob2Heidi, "out");
        messageRepository.save(message1);
        message2 = new Message("Hello Bob I am Heidi", conversationHeidi2Bob, "in");
        messageRepository.save(message2);
        conversationBob2Heidi.addMessages(message1);
        conversationHeidi2Bob.addMessages(message2);
        
        message1 = new Message("Hi Heidi I am Bob", conversationBob2Heidi, "in");
        messageRepository.save(message1);
        message2 = new Message("Hi Heidi I am Bob", conversationHeidi2Bob, "out");
        messageRepository.save(message2);
        conversationBob2Heidi.addMessages(message1);
        conversationHeidi2Bob.addMessages(message2);

    }
}
