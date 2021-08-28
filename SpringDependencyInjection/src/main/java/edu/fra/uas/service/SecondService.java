package edu.fra.uas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SecondService {
	
	private static final Logger log = LoggerFactory.getLogger(SecondService.class);
	
	public void doSomething() {
		log.info("secondService --> doSomething()");
	}

}
