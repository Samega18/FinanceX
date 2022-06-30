package com.milos.financex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milos.financex.domain.Api;
import com.milos.financex.repository.ApiRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor @Transactional
public class ApiService {
	
	@Autowired
	private ApiRepository repository;
	
	@Transactional(readOnly = true)
	public List<Api> findAll(){
		List<Api> result = repository.findAll();
		return result;
	}
	
	public Api saveApi(Api api) {
		// TODO Auto-generated method stub
		return repository.save(api);
	}
	
}
