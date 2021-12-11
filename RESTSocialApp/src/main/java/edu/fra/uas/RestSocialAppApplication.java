package edu.fra.uas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.COLLECTION_JSON)
public class RestSocialAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestSocialAppApplication.class, args);
	}

}
