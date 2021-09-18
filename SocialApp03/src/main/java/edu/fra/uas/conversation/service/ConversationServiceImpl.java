package edu.fra.uas.conversation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.conversation.model.ConversationUser;
import edu.fra.uas.conversation.repository.ConversationRepository;
import edu.fra.uas.message.model.Message;

@Service
public class ConversationServiceImpl implements ConversationService {
	
	private static final Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);
	
	private ConversationRepository conversationRepository;
	
	@Autowired
	public ConversationServiceImpl(ConversationRepository conversationRepository) {
		log.debug("ConversationService instantiated");
		this.conversationRepository = conversationRepository;
	}

	@Override
	public void newConversationBetween(ConversationUser userTo, ConversationUser userFrom) {
		log.debug("create a new conversation between " + userTo + " and " + userFrom);
		Conversation conversationTo = new Conversation(userTo.getNickname(), userFrom);
		Conversation conversationFrom = new Conversation(userFrom.getNickname(), userTo);
		if(userFrom.addConversation(conversationTo)) {
			conversationRepository.save(conversationTo);
		}
		if(userTo.addConversation(conversationFrom)) {
			conversationRepository.save(conversationFrom);
		}
	}

	@Override
	public void deleteConversationBetween(ConversationUser userFrom, ConversationUser userTo) {
		log.debug("delete conversation between " + userFrom + " and " + userTo);
		String from = userFrom.getNickname();
		String to = userTo.getNickname();
		Conversation conversationTo = userTo.getConversations().get(from);
		Conversation conversationFrom = userFrom.getConversations().get(to);
		userTo.getConversations().remove(from);
		userFrom.getConversations().remove(to);
		conversationRepository.delete(conversationTo);
		conversationRepository.delete(conversationFrom);		
	}

	@Override
	public void saveMessages(Conversation conversation, String content, String direction) {
		log.debug("save message: " + content + " with direction: " + direction + " in " + conversation.toString());
		Message message = new Message(content, conversation, direction);
		conversation.addMessages(message);
		conversationRepository.save(conversation);
	}

	@Override
	public void resetNewMessages(String from, String to) {
		log.debug("reset new messages in conversation between " + from + " and " + to);
		conversationRepository.findByConversationUser_NicknameAndConversationWith(from, to)
				.ifPresent(conversation -> {
					conversation.resetNewMessages();
					conversationRepository.save(conversation);
					return;
				});
	}

}
