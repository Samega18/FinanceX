package com.milos.financex.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milos.financex.domain.MensageContact;
import com.milos.financex.service.MensageContactService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController 
@RequestMapping(value = "/contact")
@RequiredArgsConstructor
public class MensageContactResource {
	
	private final MensageContactService service;
	
	@PostMapping("/new")
	public ResponseEntity<MensageContact> updateUser(@RequestBody MensageContact mensageContact){
		service.saveMensage(mensageContact);
		return ResponseEntity.ok().body(mensageContact);
	}
	
}
