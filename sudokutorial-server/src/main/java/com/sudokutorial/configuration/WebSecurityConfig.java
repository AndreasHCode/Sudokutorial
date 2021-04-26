package com.sudokutorial.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.sudokutorial.service.PlayerService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
			.antMatchers("/", "/home", "/grid")
				.permitAll()
			.antMatchers("/hello", "/gridCells")
				.authenticated()
			.antMatchers("/helloAdmin")
				.hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/login")
				.failureUrl("/login?error")
				.and()
			.logout()
				.permitAll();
		
	}

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(playerService).passwordEncoder(passwordEncoder);
    }

    @Autowired
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
