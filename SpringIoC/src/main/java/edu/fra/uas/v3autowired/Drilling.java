package edu.fra.uas.v3autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Drilling implements Work {

	private static final Logger log = LoggerFactory.getLogger(Drilling.class);
	
	public void doWork() {
		log.info(" --> drill a hole into the wall");
	}
	
}
