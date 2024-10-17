package ir.mywallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
						.requestMatchers("api/**").permitAll()
						.anyRequest().authenticated());
		
		return http.build();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder(); // Password encoding
//	}
	
//	@Bean
//	public AuthenticationProvider authenticationProvider(){
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService());
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
//		return config.getAuthenticationManager();
//	}
	
}
