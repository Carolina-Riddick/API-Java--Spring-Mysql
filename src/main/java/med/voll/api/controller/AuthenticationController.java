package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.user.User;
import med.voll.api.domain.user.UserAuthenticationData;
import med.voll.api.infra.security.JWTtokenData;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationData userAuthenticationData){
		Authentication token = new UsernamePasswordAuthenticationToken(userAuthenticationData.username(),userAuthenticationData.psw());
		var authenticatedUser = authenticationManager.authenticate(token);
		var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		System.out.println(JWTtoken);
		return ResponseEntity.ok(new JWTtokenData(JWTtoken));		
	}
}