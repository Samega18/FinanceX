package com.milos.financex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milos.financex.domain.MensageContact;
import com.milos.financex.repository.MensageContactRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class MensageContactService {
	
	@Autowired
	private MensageContactRepository repository;
	
	public MensageContact saveMensage(MensageContact mensageContact) {
		// TODO Auto-generated method stub
		return repository.save(mensageContact);
	}
	
}
