package com.milos.financex.api;

import java.io.IOException;
import java.lang.ProcessHandle.Info;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.milos.financex.domain.ConfirmationToken;
import com.milos.financex.domain.Role;
import com.milos.financex.domain.TokenData;
import com.milos.financex.domain.UpdateUser;
import com.milos.financex.domain.User;
import com.milos.financex.repository.ConfirmationTokenRepository;
import com.milos.financex.repository.UserRepository;
import com.milos.financex.service.EmailSenderService;
import com.milos.financex.service.UserService;
import com.milos.financex.service.UserServiceImple;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController 
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserResource {

	private final UserService userService;
	private final UserServiceImple userServiceImple;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
	
	@GetMapping("/get-all")
	public ResponseEntity<List<User>>getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
		
	}
	
	@PostMapping("/get")
	public ResponseEntity<User>getUser(@RequestBody TokenData token){
		DecodedJWT jwt;
		String email = null;
		String tokenValue = token.getToken();
		
		try {
			jwt = JWT.decode(tokenValue);
			email = jwt.getSubject();
		} catch (JWTDecodeException exception){
		    //Invalid token
		}
		
		User user = userService.getUser(email);
		user.setPassword(null);
		
		return ResponseEntity.ok().body(user);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user){
		//user.setImage(null);
		userService.updateUser(user);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody User user, HttpServletResponse response){
		try {
			URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString());
			
			String res = userService.saveUser(user);
			
			if(res == "Saved") {
				
				ConfirmationToken confirmationToken = new ConfirmationToken(user);

	            confirmationTokenRepository.save(confirmationToken);

	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setTo(user.getEmail());
	            mailMessage.setSubject("Complete Registration!");
	            mailMessage.setFrom("samuelmarquesssrpc@outlook.com");
	            mailMessage.setText("To confirm your account, please click here : "
	            +"http://10.5.55.117:8080/user/confirm?token="+confirmationToken.getConfirmationToken());

	            emailSenderService.sendEmail(mailMessage);
				
				return ResponseEntity.created(uri).body("Saved");
				
			}else if(res == "UserExists") {
				throw new Exception("UserExists");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(
			           HttpStatus.CONFLICT, "User Exists Already", e);
		}
		return null;
	}
	
	@GetMapping("/confirm")
    public ResponseEntity<?> confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        String data = null;

        if(token != null)
        {
            User user = userService.getUser(token.getUser().getEmail());
            user.setEnabled(true);
            userService.updateUser(user);
            data = "accountVerified";
        }
        else
        {
        	//modelAndView.addObject("message","The link is invalid or broken!");
            //modelAndView.setViewName("error");
        	data = "InvalidOrBroken";
        }

        return ResponseEntity.ok().body(data);
    }
	
	@PostMapping("/del")
	public ResponseEntity<User>deleteUser(@RequestBody User user){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/del").toUriString());
		return ResponseEntity.created(uri).body(userService.removeUser(user));
	}
	
	@PostMapping("/role/save")
	public ResponseEntity<Role>saveRole(@RequestBody Role role){
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
		return ResponseEntity.created(uri).body(userService.saveRole(role));
	}
	
	@PostMapping("/role/addtouser")
	public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
		userService.addRoleToUser(form.getEmail(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refresh_token);
				String email = decodedJWT.getSubject();
				User user = userService.getUser(email);
				String access_token = JWT.create()
						.withSubject(user.getEmail())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))//Duração de 10 Minutos do token
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
						.sign(algorithm);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				
			} catch (Exception e) {
				// TODO: handle exception
				response.setHeader("error", e.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				//response.sendError(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
			
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
		
	}
	
//	@PostMapping("/image/save")
//	User uploadImage(@RequestParam("token") String token, @RequestParam("image") MultipartFile multipartImage) throws Exception {
//		
//		User user = userServiceImple.FindUserWithToken(token);
//		user.setImage(multipartImage.getBytes());
//
//	    return userService.updateUser(user);
//	}
//	
//	@PostMapping(value = "/image/user", produces = MediaType.IMAGE_JPEG_VALUE)
//    ByteArrayResource downloadImage(@RequestBody TokenData token) {
//		User user = userServiceImple.FindUserWithToken(token);
//        byte[] image = user.getImage();
//
//        return new ByteArrayResource(image);
//    }
	
//	@PostMapping("/image/save")
//	Long uploadImage(@RequestParam("image") MultipartFile multipartImage) throws Exception {
//	     Image dbImage = new Image();
//	     dbImage.setName(multipartImage.getName());
//	     dbImage.setContent(multipartImage.getBytes());
//
//	     return imageRepository.save(dbImage)
//	         .getId();
//	 }
	
}

@Data 
class RoleToUserForm{
	private String email;
	private String roleName;
}
