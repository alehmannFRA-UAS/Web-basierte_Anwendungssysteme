package edu.fra.uas;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationInitializer {
	
	public static void main(String[] args) {
		ApplicationInitializer applicationInitializer = new ApplicationInitializer();
		//applicationInitializer.startupXML();
		applicationInitializer.startupAnnotation();
	}
	
	public void startupXML() {
		// XML Version
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Application application = (Application) context.getBean("application");
		String name = application.getName();
		System.out.println(name);
		
		context.close();
	}
	
	public void startupAnnotation() {
		// Annotation Version
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(ApplicationConfig.class);
		context.refresh();
		
		Application application = (Application) context.getBean("application");
		application.setName("### I am an application defined by Annotation! ###");
		String name = application.getName();
		System.out.println(name);
		
		context.close();
	}

}
