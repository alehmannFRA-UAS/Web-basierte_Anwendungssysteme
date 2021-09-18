package edu.fra.uas.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByEmail(String email);
	
	boolean existsByEmail(String email);
	
	boolean existsByNickname(String nickname);
	
	List<User> findAllByOrderByNicknameAsc();
	
}
