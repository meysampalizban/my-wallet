package ir.mywallet.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("api/**").permitAll()
				.anyRequest().authenticated());
		return http.build();
	}
	
	@Bean
	public JwtRequestFilter jwtRequestFilter(){
		return new JwtRequestFilter();
	}
	
	@Bean
	public FilterRegistrationBean loggingFilter(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(this.jwtRequestFilter());
		registrationBean.addUrlPatterns("/api/transaction/**","/api/account/**","/api/user/getuser/**");
		return registrationBean;
	}

}
