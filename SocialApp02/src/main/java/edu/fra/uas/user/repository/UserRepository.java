package edu.fra.uas.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.fra.uas.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByName(String name);

}
