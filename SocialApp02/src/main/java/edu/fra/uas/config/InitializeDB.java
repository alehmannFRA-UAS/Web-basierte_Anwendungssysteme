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

        User user1 = new User();
        User user2 = new User();
            
        user1.setName("donald");            
        user2.setName("bob");
            
        userRepository.save(user1);
        userRepository.save(user2);            

        Conversation conversation1 = new Conversation(user2.getName(), user1);
        Conversation conversation2 = new Conversation(user1.getName(), user2);

        user1.addConversation(conversation1);
        user2.addConversation(conversation2);
            
        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);
        
        // start conversation
        String timestamp = LocalTime.now().minusMinutes(14).plusSeconds(12).format(germanFormatter);
        Message messageOut = new Message("Hello Bob", timestamp, "out");
        messageRepository.save(messageOut);
        conversation1.addMessages(messageOut);            
        Message messageIn = new Message("Hello Bob", timestamp, "in");
        messageRepository.save(messageIn);            
        conversation2.addMessages(messageIn);            

        timestamp = LocalTime.now().minusMinutes(13).plusSeconds(59).format(germanFormatter);
        messageIn = new Message("Dear Mr Trump", timestamp, "in");
        messageRepository.save(messageIn);
        conversation1.addMessages(messageIn);            
        messageOut = new Message("Dear Mr Trump", timestamp, "out");
        messageRepository.save(messageOut);            
        conversation2.addMessages(messageOut);
        
        timestamp = LocalTime.now().minusMinutes(13).plusSeconds(47).format(germanFormatter);
        messageIn = new Message("do you believe in climate change?", timestamp, "in");
        messageRepository.save(messageIn);
        conversation1.addMessages(messageIn);            
        messageOut = new Message("do you believe in climate change?", timestamp, "out");
        messageRepository.save(messageOut);            
        conversation2.addMessages(messageOut);
        
        timestamp = LocalTime.now().minusMinutes(13).plusSeconds(11).format(germanFormatter);
        messageOut = new Message("It's freezing and snowing in New York", timestamp, "out");
        messageRepository.save(messageOut);
        conversation1.addMessages(messageOut);            
        messageIn = new Message("It's freezing and snowing in New York", timestamp, "in");
        messageRepository.save(messageIn);            
        conversation2.addMessages(messageIn);
        
        timestamp = LocalTime.now().minusMinutes(12).plusSeconds(37).format(germanFormatter);
        messageOut = new Message("We need global warming", timestamp, "out");
        messageRepository.save(messageOut);
        conversation1.addMessages(messageOut);            
        messageIn = new Message("We need global warming", timestamp, "in");
        messageRepository.save(messageIn);            
        conversation2.addMessages(messageIn); 

        conversationRepository.save(conversation1);
        conversationRepository.save(conversation2);
    }
}
