package com.milos.financex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milos.financex.domain.Role;

public interface RoleRepository extends JpaRepository<Role ,Long>{
	
	Role findByName(String name);
}
