package edu.fra.uas.config;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.repository.ConversationRepository;
import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.repository.MessageRepository;
import edu.fra.uas.user.model.User;
import edu.fra.uas.user.repository.UserRepository;

@Component
public class InitializeDB {

    private static final Logger log = LoggerFactory.getLogger(InitializeDB.class);

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ConversationRepository conversationRepository;
    
    @Autowired
    MessageRepository messageRepository;

    @PostConstruct
    public void init()  {
    	log.debug(" >>> init database");
            
        DateTimeFormatter germanFormatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);
        String timestamp30 = LocalTime.now().minusMinutes(30).format(germanFormatter);
        String timestamp28 = LocalTime.now().minusMinutes(28).format(germanFormatter);

        User user1 = new User();
        User user2 = new User();
            
        user1.setName("alice");            
        user2.setName("bob");
            
        userRepository.save(user1);
        userRepository.save(user2);            

        Conversation conversation1 = new Conversation(user2.getName(), user1);
        Conversation conversation2 = new Conversation(user1.getName(), user2);

        user1.addConversation(conversation1);
        user2.addConversation(conversation2);
            
        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);

        Message message1 = new Message("Hello Bob", timestamp30, "out");
        messageRepository.save(message1);
        conversation1.addMessages(message1);
            
        Message message2 = new Message("Hello Bob", timestamp30, "in");
        messageRepository.save(message2);            
        conversation2.addMessages(message2);            

        Message message3 = new Message("Hi Alice", timestamp28, "in");
        messageRepository.save(message3);
        conversation1.addMessages(message3);
            
        Message message4 = new Message("Hi Alice", timestamp28, "out");
        messageRepository.save(message4);            
        conversation2.addMessages(message4);

        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);
    }
}
