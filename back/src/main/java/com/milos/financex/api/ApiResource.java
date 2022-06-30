package com.milos.financex.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milos.financex.domain.Api;
import com.milos.financex.service.ApiService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController 
@RequestMapping(value = "/api2")
@RequiredArgsConstructor
public class ApiResource {
	
	private final ApiService service;
	
	@GetMapping("/info")
	public ResponseEntity<List<Api>>getApi(){
		return ResponseEntity.ok().body(service.findAll());
		
	}

}
