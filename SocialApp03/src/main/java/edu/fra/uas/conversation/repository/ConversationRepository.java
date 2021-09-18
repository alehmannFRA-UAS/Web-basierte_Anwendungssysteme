package edu.fra.uas.conversation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.conversation.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByConversationUser_NicknameAndConversationWith(String from, String to);
    
}
