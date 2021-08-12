package com.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class OauthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthClientApplication.class, args);
	}

	@RestController
	public static class TestController {

		@GetMapping("/")
		public Object home() {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return principal;
		}

	}

}
