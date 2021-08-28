package edu.fra.uas.session;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = "prototype") //prototype, singleton, request
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Session {

	private static final Logger log = LoggerFactory.getLogger(Session.class);
	
	private String sessionName;
	
	public Session() {
		sessionName = UUID.randomUUID().toString();
		log.info("Session Bean constructed --> sessionName is: " +  sessionName);
	}
	
	public String getSessionName() {
		return sessionName;
	}
	
}
