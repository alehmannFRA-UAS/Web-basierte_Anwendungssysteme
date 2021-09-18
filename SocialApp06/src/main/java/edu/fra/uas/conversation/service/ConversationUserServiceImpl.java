package edu.fra.uas.conversation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.repository.ConversationUserRepository;
import edu.fra.uas.conversation.service.dto.ConversationDTO;
import edu.fra.uas.security.model.UserCreateForm;

@Service
public class ConversationUserServiceImpl implements ConversationUserService {
	
	private static final Logger log = LoggerFactory.getLogger(ConversationUserServiceImpl.class);
	
	private ConversationUserRepository conversationUserRepository;
	
	private ConversationService conversationService;
	
	@Autowired
	public ConversationUserServiceImpl(ConversationUserRepository conversationUserRepository, ConversationService conversationService) {
		log.debug("ConversationUserService instantiated");
		this.conversationUserRepository = conversationUserRepository;
		this.conversationService = conversationService;
	}

	@Override
	public ConversationUser getByNickname(String nickname) {
		log.debug("get user by nickname: " + nickname);
		return conversationUserRepository.findByNickname(nickname).orElse(null);
	}

	@Override
	public boolean existsNickname(String to) {
		log.debug("does user " + to + " exist? : " + conversationUserRepository.findByNickname(to));
		return conversationUserRepository.existsByNickname(to);
	}

	@Override
	public boolean conversationUserConversationsContainsKeyfindByNickname(String from, String to) {
		log.debug("does user conversations contain user " + to + " as key?");
		Optional<ConversationUser> userOptional = conversationUserRepository.findByNickname(from);
		if (userOptional.isPresent()) {
			return userOptional.get().getConversations().containsKey(to);
		}
		return false;
	}

	@Override
	public void newConversationFromTo(String from, String to) {
		log.debug("new conversation between " + from + " and " + to);
		ConversationUser userFrom = conversationUserRepository.findByNickname(from).orElse(null);
		ConversationUser userTo = conversationUserRepository.findByNickname(to).orElse(null);
		conversationService.newConversationBetween(userTo, userFrom);
	}

	@Override
	public void deleteConversationFromTo(String from, String to) {
		log.debug("delete conversation between " + from + " and " + to);
		ConversationUser userFrom = conversationUserRepository.findByNickname(from).orElse(null);
		ConversationUser userTo = conversationUserRepository.findByNickname(to).orElse(null);
		conversationService.deleteConversationBetween(userFrom, userTo);
	}

	@Override
	public Conversation getConversationFromByNicknameTo(String from, String to) {
		log.debug("get conversation from " + from + " by name to " + to);
		Optional<ConversationUser> userOptional = conversationUserRepository.findByNickname(to);
		if(userOptional.isPresent()) {
			return userOptional.get().getConversations().get(from);
		}
		return null;
	}

	@Override
	public void createConversationsUser(UserCreateForm form) {
		log.debug("createConversationUser " + form.getNickname());
		ConversationUser conversationUser = new ConversationUser();
		conversationUser.setNickname(form.getNickname());
		conversationUserRepository.save(conversationUser);
	}

	@Override
	public List<ConversationDTO> getAllConversationsFrom(String from) {
		log.debug("getAllConversationsFrom from " + from);
		ConversationUser userFrom = getByNickname(from);
		List<Conversation> targetListOrigin = new ArrayList<>(userFrom.getConversations().values()); 
		List<ConversationDTO> targetList= new ArrayList<ConversationDTO>(); 
		for (Conversation source: targetListOrigin ) {
	        ConversationDTO target= new ConversationDTO();
	        BeanUtils.copyProperties(source , target);    
	        targetList.add(target);
	    };
		Collections.sort(targetList, new Comparator<ConversationDTO>() {
		    public int compare(ConversationDTO o1, ConversationDTO o2) {
		           return o1.getConversationWith().compareTo(o2.getConversationWith());
		    }
		});		
		return targetList;

	}

}
