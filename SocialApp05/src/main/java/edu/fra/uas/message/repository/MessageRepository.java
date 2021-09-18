package edu.fra.uas.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.message.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
