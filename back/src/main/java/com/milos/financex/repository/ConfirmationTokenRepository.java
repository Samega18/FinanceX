package com.milos.financex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milos.financex.domain.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken , String>{

	ConfirmationToken findByConfirmationToken(String confirmationToken);
	
}
