package com.authservice.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf().disable() // Disable CSRF protection (cross-site request forgery) for simplicity.
	            .authorizeHttpRequests() // Begin configuring authorization rules.
	            .antMatchers("/auth/register", "/auth/token", "/auth/validate").permitAll()
	            // Specify that the following URLs ("/auth/register", "/auth/token", "/auth/validate")
	            // should be accessible to anyone without requiring authentication (permitAll).
	            .and() // End authorization rule configuration.
	            .build(); // Build and return the SecurityFilterChain.
	}
	
	  @Bean
	    public UserDetailsService userDetailsService(){
	        return new CustomUserDetailsService();//create ur own userDetailService who will connect to the db and will give the information to the authentication provider and authentication provider can connect back to the autehntication manager that is how it can authenticate 
	    }

	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	 

	    @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(getPasswordEncoder());
	        return authenticationProvider;
	    }
	 
	 
}
