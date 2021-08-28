package edu.fra.uas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstService {

	@Autowired
	private SecondService secondService;
	
	public FirstService() {}
	
	// Constructor Injection
//	@Autowired
//	public FirstService(SecondService secondService) {
//		this.secondService = secondService;
//	}
	
	// Setter Injection
//	@Autowired
//	public void setSecondService(SecondService secondService) {
//		this.secondService = secondService;
//	}
	
	public void doSomething() {
		secondService.doSomething();
	}
	
}
