package edu.fra.uas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Equivalent zu:
 *  <bean id="application" class="edu.fra.uas.Application">
 *   
 *  </bean>
 */
@Configuration
public class ApplicationConfig {

	@Bean
	public Application application() {
		return new Application();		
	}
	
}
