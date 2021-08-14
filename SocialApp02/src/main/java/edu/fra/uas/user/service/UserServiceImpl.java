package edu.fra.uas.user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.service.ConversationService;
import edu.fra.uas.user.model.User;
import edu.fra.uas.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository userRepository;
	
	private ConversationService conversationService;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, ConversationService conversationService) {
		log.debug("UserService instantiated");
		this.userRepository = userRepository;
		this.conversationService = conversationService;
	}

	@Override
	public User getByName(String name) {
		log.debug("get user by name: " + name);
		return userRepository.findByName(name).orElse(null);
	}

	@Override
	public Object existsName(String to) {
		log.debug("does user " + to + " exist? : " + userRepository.findByName(to));
		return userRepository.findByName(to);
	}

	@Override
	public boolean userConversationsContainsKeyfindByName(String from, String to) {
		log.debug("does user conversations contain user " + to + " as key?");
		Optional<User> userOptional = userRepository.findByName(from);
		if (userOptional.isPresent()) {
			return userOptional.get().getConversations().containsKey(to);
		}
		return false;
	}

	@Override
	public void newConversationFromTo(String from, String to) {
		log.debug("new conversation between " + from + " and " + to);
		User userFrom = userRepository.findByName(from).orElse(null);
		User userTo = userRepository.findByName(to).orElse(null);
		conversationService.newConversationBetween(userTo, userFrom);
	}

	@Override
	public void deleteConversationFromTo(String from, String to) {
		log.debug("delete conversation between " + from + " and " + to);
		User userFrom = userRepository.findByName(from).orElse(null);
		User userTo = userRepository.findByName(to).orElse(null);
		conversationService.deleteConversationBetween(userFrom, userTo);
	}

	@Override
	public Conversation getConversationFromByNameTo(String from, String to) {
		log.debug("get conversation from " + from + " by name to " + to);
		Optional<User> userOptional = userRepository.findByName(to);
		if(userOptional.isPresent()) {
			return userOptional.get().getConversations().get(from);
		}
		return null;
	}

}
