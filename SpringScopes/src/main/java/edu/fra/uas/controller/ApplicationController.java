package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.fra.uas.session.Session;

@Controller
public class ApplicationController {

	private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
	
	@Autowired
	ApplicationContext applicationContext;
	
	public ApplicationController() {}
	
	@RequestMapping(value = "/path1")
	public String showPage1() {
		Session session1 = (Session) applicationContext.getBean(Session.class);
		Session session2 = (Session) applicationContext.getBean(Session.class);
		log.info(session1.getSessionName());
		log.info(session2.getSessionName());
		return "page";
	}
	
	@RequestMapping(value = "/path2")
	public String showPage2() {
		Session session1 = (Session) applicationContext.getBean(Session.class);
		Session session2 = (Session) applicationContext.getBean(Session.class);
		log.info(session1.getSessionName());
		log.info(session2.getSessionName());
		return "page";
	}
	
}
