package com.harajuku.messagingApp.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.harajuku.messagingApp.App;
import com.harajuku.messagingApp.model.User;
import com.harajuku.messagingApp.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private AuthenticationSuccessHandler successHandler;
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	@Autowired
	UserRepository userRep;

	public SecurityConfig(CustomAuthenticationSuccessHandler sh) {
		this.successHandler = sh;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {
		http.authorizeRequests().antMatchers("/login", "/register").permitAll() // Allow unauthenticated access to login
				.antMatchers("/landingPage").authenticated() // Require authentication for
				.antMatchers("/chatRoom").authenticated()
//				.antMatchers("/chat/**").permitAll()
				.anyRequest().authenticated()																			// other pages
				.and().formLogin().loginPage("/login") // Default login page
				.loginProcessingUrl("/login") // Spring Security's default login processing URL
				.successHandler(successHandler)
				.failureUrl("/login?error=true") // Redirect to login page on failure
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.permitAll(); }
		catch(Exception ex) {
			logger.error("cause: " + ex.getCause());;
		}
	}

	@Override
	protected UserDetailsService userDetailsService() {
		return username -> {
			User user = userRep.findByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException("User not found");
			}
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), // Encoded
					Arrays.asList(new SimpleGrantedAuthority(user.getRole())) // Assuming user has a role
			);
		};
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		try {
			auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		} catch(Exception ex) {
			logger.error("cause: " + ex.getCause());
		}
		
	}
}
