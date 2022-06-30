package com.milos.financex.service;

import java.util.List;

import com.milos.financex.domain.Role;
import com.milos.financex.domain.TokenData;
import com.milos.financex.domain.User;

public interface UserService {
	
	String saveUser(User user);
	
	Role saveRole(Role role);
	
	void addRoleToUser(String email, String roleName);
	
	User getUser(String email);
	
	List<User> getUsers();
	
	User removeUser(User user);
	
	User setAdress(String adress);

	User setImage(String image);

	User updateUser(User user);

	User FindUserWithToken(TokenData token);

	User FindUserWithToken(String token);

}
