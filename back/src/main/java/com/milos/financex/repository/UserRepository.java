package com.milos.financex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milos.financex.domain.User;

public interface UserRepository extends JpaRepository<User ,Long>{
	
	User findByEmail(String email);
	
}
