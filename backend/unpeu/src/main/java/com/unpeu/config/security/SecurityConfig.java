package com.unpeu.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.unpeu.config.auth.JwtAuthenticationFilter;
import com.unpeu.config.auth.UnpeuUserDetailService;
import com.unpeu.service.iface.IUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UnpeuUserDetailService unpeuUserDetailService;

	@Autowired
	private IUserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.unpeuUserDetailService);
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager(), userService))
			.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/api/users").authenticated()
			.antMatchers(HttpMethod.DELETE,"/api/users").authenticated()
			.antMatchers("/api/users/email/birth").authenticated()
			.antMatchers("/api/users/img").authenticated()
			.antMatchers("/api/users/info").authenticated()
			.antMatchers("/api/users/music").authenticated()
			.antMatchers("/api/users/title").authenticated()
			.antMatchers(HttpMethod.POST,"/api/message/messageToDiary").authenticated()
			.antMatchers(HttpMethod.DELETE,"/api/message").authenticated()
			.antMatchers(HttpMethod.POST,"/api/present").authenticated()
			.antMatchers(HttpMethod.PUT,"/api/present/{presentId}").authenticated()
			.antMatchers(HttpMethod.DELETE,"/api/present/{presentId}").authenticated()
			.antMatchers(HttpMethod.POST,"/api/board").authenticated()
			.antMatchers(HttpMethod.PUT,"/api/board/{boardId}").authenticated()
			.antMatchers(HttpMethod.DELETE,"/api/board/{boardId}").authenticated()
			.and().cors();
	}
}
