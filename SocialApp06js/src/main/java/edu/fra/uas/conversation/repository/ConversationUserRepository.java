package edu.fra.uas.conversation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.conversation.model.ConversationUser;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {
	
	Optional<ConversationUser> findByNickname(String nickname);
	
	boolean existsByNickname(String to);

}
