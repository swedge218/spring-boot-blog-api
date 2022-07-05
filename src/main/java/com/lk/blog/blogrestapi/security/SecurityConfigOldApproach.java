package com.lk.blog.blogrestapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class extends the WebSecurityConfigurerAdapter which is now deprecated.
 * The new approach does not extend it and so does not require overriding its methods.
 * 
 * @Configuration, @EnableWebSecurity and @EnableGlobalMethodSecurity(prePostEnabled = true)
 * annotations are commented out so that the class is not loaded into the classpath and does not disrupt the 
 * new implementation. If using this class (the old approach) uncomment them before use.
 *  
 * @author leke
 *
 */

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigOldApproach extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
			.antMatchers("/api/auth/**").permitAll()
//			.antMatchers("/**").denyAll()
			.anyRequest()
			.authenticated()
			.and()
			.logout()
			.logoutUrl("/api/auth/signout")
			.logoutSuccessUrl("/api/auth/sign-out-success")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");
//			.and()
//			.httpBasic();
		
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	// This is for authentication on requests
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	// this is for sign-in
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

	/**
	 * USE METHOD IF USING INMEMORY AUTHENTICATION
	 */
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		UserDetails user = User.builder().username("user")
//				.password(passwordEncoder().encode("password")).roles("USER").build();
//		UserDetails admin = User.builder().username("admin")
//				.password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//	}
	
}
