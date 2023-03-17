package com.rcode.discoveryservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${eureka.username}")
	private String username;
	
	@Value("${eureka.password}")
	private String password;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		return http.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser(username).password(password).authorities("USER");
	}

//	@Bean
//	public UserDetailsService users() {
//		UserDetails user = User.builder().username("user").password("user").roles("USER").build();
//		UserDetails admin = User.builder().username("admin").password("admin").roles("USER", "ADMIN").build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//		authProvider.setUserDetailsService(users());
////	      authProvider.setPasswordEncoder(passwordEncoder());
//
//		return authProvider;
//	}

}
