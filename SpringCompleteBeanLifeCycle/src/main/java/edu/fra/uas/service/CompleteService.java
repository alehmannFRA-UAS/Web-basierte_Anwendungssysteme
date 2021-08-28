package edu.fra.uas.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class CompleteService implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean{

	private static final Logger log = LoggerFactory.getLogger(CompleteService.class);
	
	private String privateAttribute = "INIT";
	
	public void setPrivateAttribute(String privateAttribute) {
		this.privateAttribute = privateAttribute;
	}
	
	@Override
	public void setBeanName(String name) {
		log.info("setBeanName() --> " + name);		
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.info("setBeanFactory()");		
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		log.info("setApplicationContext()");		
	}
	
	@PostConstruct
    public void postConstruct() {
		log.info("postConstruct() --> " + privateAttribute);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet()");	
	}
	
	public void doSomething() {
		log.info("doSomething() --> "+ privateAttribute);
	}
	
	@PreDestroy
    public void preDestroy() {
		log.info("preDestroy()");
	}

	@Override
	public void destroy() throws Exception {
		log.info("destroy()");
	}

}
