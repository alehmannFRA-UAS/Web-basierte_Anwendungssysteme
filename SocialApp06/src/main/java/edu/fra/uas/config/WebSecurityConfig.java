package edu.fra.uas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http
        	.authorizeRequests()
        		.antMatchers("/login","/css/**","/js/**","/img/**","/webfonts/**","/favicon.ico").permitAll()
        		.antMatchers("/users/m**").hasAuthority("ADMIN")
        		.antMatchers("/users/c**").hasAuthority("ADMIN")
        		.anyRequest().authenticated()
            .and()
            .formLogin()
            	.loginPage("/login")
            	.defaultSuccessUrl("/first", true)
            	.failureUrl("/login?error")
            	.usernameParameter("email")
            	.permitAll()
            .and()
            .logout()
            	.logoutSuccessUrl("/")
            	.invalidateHttpSession(true)
            	.deleteCookies("JSESSIONID")
//          	.logoutUrl("/logout")
            	.permitAll()
            .and()
            .rememberMe();
    }
	
  
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}
