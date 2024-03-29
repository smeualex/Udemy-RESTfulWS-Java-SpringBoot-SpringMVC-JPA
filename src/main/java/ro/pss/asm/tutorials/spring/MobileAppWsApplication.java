package ro.pss.asm.tutorials.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileAppWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}
	

	@Bean
	public BCryptPasswordEncoder PasswordEncoderInitBean() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext SpringApplCtxInitBean() {
		return new SpringApplicationContext();
	}
	
}
