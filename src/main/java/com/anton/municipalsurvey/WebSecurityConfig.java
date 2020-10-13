package com.anton.municipalsurvey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				//Delete "/*", this was put in place for testing purposes
				.antMatchers("/", "/survey", "/style.css", "/jquery-3.5.1.js", "/mysqlerror", "/db", "/dbsetup").permitAll() 
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	@Bean
	public UserDetailsManager users(DataSource dataSource) {
		
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		
		return users;
	}
	/*@Bean
	public UserDetailsService userDetailsService() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		DatabaseAccess a = new DatabaseAccess();
		String[][] users = null;
		
		try {
			
			users = a.getUsers();
		} catch (SQLException e) {
			
			System.out.println(e);
			System.out.println(e);
			System.out.println(e);
			System.out.println(e);
		}
		System.out.println(encoder.encode("password"));
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		
		try {
			for(int i = 0; i < users.length; i++) {
				UserDetails db_user = User
						.withUsername(users[i][0])
						.password(users[i][3])
						.roles("USER")
						.build();
				userDetailsManager.createUser(db_user);
			}
		}
		catch(NullPointerException e) {
			System.out.println(e);
		}
		return userDetailsManager; 
	}*/
}
