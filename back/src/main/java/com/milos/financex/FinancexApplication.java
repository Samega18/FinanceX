package com.milos.financex;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.milos.financex.domain.Api;
import com.milos.financex.domain.Role;
import com.milos.financex.domain.User;
import com.milos.financex.service.ApiService;
import com.milos.financex.service.UserService;

@SpringBootApplication
public class FinancexApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancexApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean 
	CommandLineRunner run(UserService userService) { return args -> {
		  userService.saveRole(new Role(null, "ROLE_USER")); 
		  userService.saveRole(new Role(null, "ROLE_MANAGER")); 
		  userService.saveRole(new Role(null, "ROLE_ADMIN")); 
		  userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
		  
		//userService.saveUser(new User(id, name, email, password, phone, gender, rua, bairro, numero, complemento, cidade, estado, cep, isEnable, permissionPartilhar, removedUser, cart, favorite, roles));
		  userService.saveUser(new User(null, "Samuel Marques", "samuelmarquesssrpc@outlook.com", "123456", "83986466326", true, null));
		  userService.saveUser(new User(null, "Bernardo", "email@outlook.com", "123456", "83911111111", true, null)); 
		  userService.saveUser(new User(null, "Mayane", "email2@outlook.com", "123456", "83922222222", true, null)); 
		  userService.saveUser(new User(null, "Will Smith", "email3@outlook.com", "123456", "83933333333", true, null));
		  
		  userService.addRoleToUser("samuelmarquesssrpc@outlook.com","ROLE_SUPER_ADMIN");
		  userService.addRoleToUser("samuelmarquesssrpc@outlook.com", "ROLE_ADMIN");
		  userService.addRoleToUser("samuelmarquesssrpc@outlook.com", "ROLE_USER");
		  userService.addRoleToUser("email@outlook.com", "ROLE_SUPER_ADMIN");
		  userService.addRoleToUser("email2@outlook.com", "ROLE_MANAGER");
		  userService.addRoleToUser("email2@outlook.com", "ROLE_USER");
		  userService.addRoleToUser("email3@outlook.com", "ROLE_SUPER_ADMIN");
		  userService.addRoleToUser("email3@outlook.com", "ROLE_ADMIN");
		  userService.addRoleToUser("email3@outlook.com", "ROLE_USER"); 
	  
	};}
	
	@Bean 
	CommandLineRunner run2(ApiService apiService) { return args -> {
			apiService.saveApi(new Api(null, true));
	  
	};}
}
