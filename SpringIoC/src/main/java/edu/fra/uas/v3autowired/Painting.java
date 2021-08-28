package edu.fra.uas.v3autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("pleasePaint")
public class Painting implements Work {

	private static final Logger log = LoggerFactory.getLogger(Painting.class);
	
	public void doWork() {
		log.info(" --> painting the wall black");
	}
	
}
