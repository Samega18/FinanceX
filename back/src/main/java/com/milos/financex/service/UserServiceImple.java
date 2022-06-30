package com.milos.financex.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.milos.financex.domain.ConfirmationToken;
import com.milos.financex.domain.Role;
import com.milos.financex.domain.TokenData;
import com.milos.financex.domain.User;
import com.milos.financex.repository.ConfirmationTokenRepository;
import com.milos.financex.repository.RoleRepository;
import com.milos.financex.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserServiceImple implements UserService, UserDetailsService{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenRepository confirmationTokenRepository;
	private final EmailSenderService emailSenderService;
	
	@Override
	public User FindUserWithToken(TokenData token) {
		
		log.info("Token: {}", token);
		DecodedJWT jwt;
		String email = null;
		String tokenValue = token.getToken();
		
		try {
			jwt = JWT.decode(tokenValue);
			email = jwt.getSubject();
		} catch (JWTDecodeException exception){
		    //Invalid token
		}
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}
	
	@Override
	public User FindUserWithToken(String token) {
		
		DecodedJWT jwt;
		String email = null;
		
		try {
			jwt = JWT.decode(token);
			email = jwt.getSubject();
		} catch (JWTDecodeException exception){
		    //Invalid token
		}
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
			User user = userRepository.findByEmail(email);
			if(user == null) {
				log.error("User not found in the database: {}", email);
				throw new UsernameNotFoundException("User not found in the database");
			} else if(user.isEnabled() == false){
				log.info("User is disabled: {}", email);
				
				List<ConfirmationToken> token = confirmationTokenRepository.findAll();
				
				for (ConfirmationToken confirmationToken : token) {
					if(confirmationToken.getUser() == user) {
						SimpleMailMessage mailMessage = new SimpleMailMessage();
			            mailMessage.setTo(user.getEmail());
			            mailMessage.setSubject("Complete Registration!");
			            mailMessage.setFrom("samuelmarquesssrpc@outlook.com");
			            mailMessage.setText("To confirm your account, please click here : "
			            +"http://192.168.1.4:8080/user/confirm?token="+confirmationToken.getConfirmationToken());

			            emailSenderService.sendEmail(mailMessage);
					}
				}
				
				throw new UsernameNotFoundException("User not verify");
				
			}else {
				log.info("User found in the database: {}", email);
			}
			
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			user.getRoles().forEach(role -> { 
				authorities.add(new SimpleGrantedAuthority(role.getName())); 
			});
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
	@Override
	public String saveUser(User user) {
		// TODO Auto-generated method stub
		if(userRepository.findByEmail(user.getEmail()) == null) {
			log.info("Saving new user {} to the database", user.getName());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return "Saved";
		} else {
			log.info("Já existe: {}", user);
			return "UserExists";
		}
		
	}

	@Override
	public Role saveRole(Role role) {
		// TODO Auto-generated method stub
		log.info("Saving new role {} to the database", role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String email, String roleName) {
		// TODO Auto-generated method stub
		log.info("Adding role {} to user {}", roleName, email);
		User user = userRepository.findByEmail(email);
		Role role = roleRepository.findByName(roleName);
		
		user.getRoles().add(role);
	}
 
	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		log.info("Fetching user {}", email);
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		log.info("Fetching all user");
		return userRepository.findAll();
	}
	
	@Override
	public User removeUser(User user) {
		// TODO Auto-generated method stub
		User userRem = userRepository.findByEmail(user.getEmail());
		if(userRem == null) {
			log.info("User remove false: {}",user);
			log.info("email: {}",user.getEmail());
			
		} else {
			userRepository.delete(userRem);
			log.info("User remove true: {}",user);
			log.info("email: {}",user.getEmail());
		}	
		
		return user;
		
	}
	
	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		User userUpdate = userRepository.findByEmail(user.getEmail());
		user.setPassword(userUpdate.getPassword());
		
		return userRepository.save(user);
	}

	@Override
	public User setAdress(String adress) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User setImage(String image) {
		// TODO Auto-generated method stub
		return null;
	}
}
