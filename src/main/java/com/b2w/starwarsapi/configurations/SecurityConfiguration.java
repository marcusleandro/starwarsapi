package com.b2w.starwarsapi.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextListener;

import com.ixortalk.aws.cognito.boot.filter.AwsCognitoJwtAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements Ordered {
	
	
	private int order = 4;

	@Autowired
	private AwsCognitoJwtAuthenticationFilter awsCognitoJwtAuthenticationFilter;
	
	
	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	@Bean
	@Order(0)
	public RequestContextListener requestContextListener() {
	    return new RequestContextListener();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().cacheControl();
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/health").permitAll()
			.antMatchers("/v2/**").permitAll()
			.antMatchers("/docs/**").permitAll()
			.antMatchers("/api/**").authenticated()
			.antMatchers("/**").permitAll() // needs to be the last matcher, otherwise all matchers following it would never be reached
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// 	http
	// 		.authorizeRequests()
	// 			.antMatchers("/", "/home").permitAll()
	// 			.anyRequest().authenticated()
	// 			.and()
	// 		.formLogin()
	// 			.loginPage("/login")
	// 			.permitAll()
	// 			.and()
	// 		.logout()
	// 			.permitAll();
	// }

	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user =
	// 		 User.withDefaultPasswordEncoder()
	// 			.username("user")
	// 			.password("password")
	// 			.roles("USER")
	// 			.build();

	// 	return new InMemoryUserDetailsManager(user);
	// }
}
