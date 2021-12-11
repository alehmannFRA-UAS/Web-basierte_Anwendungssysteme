package edu.fra.uas.message.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.persistence.PrePersist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageListener {
	
	private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
	 
	@PrePersist
	public void methodInvokedBeforePersist(Message message) {
		log.debug("   -->   Before persist operation on post with id = {}", message.getId());
		DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);
        String s = LocalTime.now().format(germanFormatter);
        message.setTimestamp(s);
        message.setRead("false");
	}
 
//	@PostPersist
//	public void methodInvokedAfterPersist(Post post) {
//		log.debug("   -->   After persist operation on post with id = {}", post.getId());
//		if (post.getType().equals("out")) 
//			post.setRead("false");
//	}
// 
//	@PreUpdate
//	public void methodInvokedBeforeUpdate(Post post) {
//		log.debug("   -->   Before update  operation on post with id = {}", post.getId());
//	}
// 
//	@PostUpdate
//	public void methodInvokedAfterUpdate(Post post) {
//		log.debug("   -->   After update operation on post with id = {}", post.getId());
//	}
// 
//	@PreRemove
//	private void methodInvokedBeforeRemove(Post post) {
//		log.debug("   -->   Before remove operation on  post with id = {}", post.getId());
//	}
// 
//	@PostRemove
//	public void methodInvokedAfterRemove(Post post) {
//		log.debug("   -->   After remove operation on  post with id = {}", post.getId());
//	}
	
}
