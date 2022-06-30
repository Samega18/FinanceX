package com.milos.financex.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.milos.financex.filter.CustomAuthenticationFilter;
import com.milos.financex.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowedOrigins(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
		corsConfiguration.setAllowCredentials(false);
		corsConfiguration.setExposedHeaders(List.of("Authorization"));
		// You can customize the following part based on your project, it's only a sample
		//http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
		//.authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);
		
		
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/user/login");
		http.cors().configurationSource(request -> corsConfiguration);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//Permitido para todos -----------------------------------------------------------
		//Login, Register, Products, Terms, Carrinho, 
		
		//User
		http.authorizeRequests().antMatchers("/user/get").permitAll();
		http.authorizeRequests().antMatchers("/user/save").permitAll();
		http.authorizeRequests().antMatchers("/user/confirm").permitAll();
		
		
		http.authorizeRequests().antMatchers("/contact/**").permitAll();
		
		//Permitido para Usuários -----------------------------------------------------------
		
		//User
		http.authorizeRequests().antMatchers("/user/update").hasAnyAuthority("ROLE_USER");
		http.authorizeRequests().antMatchers("/user/update").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers("/user/update").hasAnyAuthority("ROLE_SUPER_ADMIN");
		
		//Permitido para Administradores(PARTILHAR) -----------------------------------------------------------
	
		
		
		//Permitido para Super Administradores(MILOS) -----------------------------------------------------------
		
		//User
		http.authorizeRequests().antMatchers("/user/del").hasAnyAuthority("ROLE_SUPER_ADMIN");
		http.authorizeRequests().antMatchers("/user/get-all").hasAnyAuthority("ROLE_SUPER_ADMIN");
		http.authorizeRequests().antMatchers("/user/role/save").hasAnyAuthority("ROLE_SUPER_ADMIN");
		http.authorizeRequests().antMatchers("/user/role/addtouser").hasAnyAuthority("ROLE_SUPER_ADMIN");
		
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
//	http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/terms/**").hasAnyAuthority("ROLE_USER");
//	http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority("ROLE_ADMIN");
//	http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save/**", "/api/user/del/**").hasAnyAuthority("ROLE_SUPER_ADMIN");
	
//	http.authorizeRequests().antMatchers("/api/image/**").permitAll();
//	http.authorizeRequests().antMatchers("/api/login").permitAll();
//	http.authorizeRequests().antMatchers("/api/user").permitAll();
//	http.authorizeRequests().antMatchers("/api/user/update").permitAll(); 
//	http.authorizeRequests().antMatchers("/api/token/refresh/**", "/api/user/save").permitAll();
//	http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/terms/**").permitAll();

}
