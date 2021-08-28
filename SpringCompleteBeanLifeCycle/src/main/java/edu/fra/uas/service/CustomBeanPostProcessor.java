package edu.fra.uas.service;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {

	private static final Logger log = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
	
	public CustomBeanPostProcessor() {
		log.info("PostProcessor --> Constructor called");
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof CompleteService) {
			log.info("PostProcessor::postProcessBeforeInitialization() --> " + beanName);
			try {
				Field field = bean.getClass().getDeclaredField("privateAttribute");
				field.setAccessible(true);
				String string = field.toGenericString();
				string = "PostProcessBeforeInitialization";
				field.set(bean, string);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	
	@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof CompleteService) {
			log.info("PostProcessor::postProcessAfterInitialization() --> " + beanName);
			((CompleteService) bean).setPrivateAttribute("PostProcessAfterInitialization");
		}
		return bean;
	}
	
}
