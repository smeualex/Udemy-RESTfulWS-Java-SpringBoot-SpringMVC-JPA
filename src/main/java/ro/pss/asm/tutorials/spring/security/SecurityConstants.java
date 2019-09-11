package ro.pss.asm.tutorials.spring.security;

import ro.pss.asm.tutorials.spring.SpringApplicationContext;

public class SecurityConstants {

	public static final long EXPIRATION_TIME = 86400 * 1000 * 10;	// 10 days (in ms)
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";		// header in HTTP request
	public static final String SIGN_UP_URL = "/api/users";			// public url
	
	public static String getTokenSecret() {
		
		AppProperties appProps = (AppProperties) SpringApplicationContext.getBean("appProperties");
		return appProps.getTokenSecret();
	}
}
