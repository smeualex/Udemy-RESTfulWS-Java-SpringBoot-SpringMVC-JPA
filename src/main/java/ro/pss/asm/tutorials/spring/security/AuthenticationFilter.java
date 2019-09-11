package ro.pss.asm.tutorials.spring.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ro.pss.asm.tutorials.spring.SpringApplicationContext;
import ro.pss.asm.tutorials.spring.service.UserService;
import ro.pss.asm.tutorials.spring.ui.model.request.UserLoginRequestModel;
import ro.pss.asm.tutorials.spring.ui.model.shared.dto.UserDto;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public final AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			UserLoginRequestModel creds = new ObjectMapper()
					.readValue(request.getInputStream(), UserLoginRequestModel.class);
			
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(),
							new ArrayList<>())
					);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, 
											HttpServletResponse response, 
											FilterChain chain,
											Authentication authResult) 
													throws IOException, ServletException {
		// get the username from the authentication result object
		String userName = ((User)authResult.getPrincipal()).getUsername();
		// get userDto
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDto = userService.getUser(userName);
		
		// generate a token for the user
		String token = Jwts.builder()
				.setSubject(userName)																		// user name
				.claim("userId", userDto.getPublicUserId())														// user id
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))	// expiration date
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())						// sign the token: SHA512 + Secret
				.compact();																					// build it
		
		// add the token in the response headers
		response.addHeader(SecurityConstants.HEADER_STRING, 
						   SecurityConstants.TOKEN_PREFIX + token);
		// add the public user id to the response headers
		response.addHeader("UserID", userDto.getPublicUserId());
	}
	
	
	
}
