package ro.pss.asm.tutorials.spring.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			// go to next filter
			chain.doFilter(request, response);
			return;
		}
		
		// get the authentication
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		// set the auth. details in the security context holder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// go to the next filter
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		// get the authorization header token
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if(token != null) {
		
			// remove the "Bearer " prefix
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			
			// parse the data and extract the user
			// decrypt with the secret used to encrypt
			Claims claims = Jwts.parser()
					.setSigningKey(SecurityConstants.getTokenSecret())
					.parseClaimsJws(token)
					.getBody();
			
			String user = claims.getSubject();
			return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			
// TODO: fix this! Use token of 1 user with user id of another !!!
//			String requestURI =  request.getRequestURI();
//			String userIdFromRequest = requestURI.substring(requestURI.lastIndexOf('/') + 1);
//			String userIdFromToken = (String)claims.get("userId");
//			
//			// compare userid received in the request with the one contained in the token
//			if(user != null && userIdFromToken.equals(userIdFromRequest)) {
//				// return new instance of AuthenticationToken
//				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//			}
		}
		
		return null;
	}
	
	
	
}
