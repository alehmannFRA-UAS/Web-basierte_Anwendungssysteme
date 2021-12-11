package edu.fra.uas.common;

import org.springframework.ui.Model;

import edu.fra.uas.security.model.CurrentUser;

public class CurrentUserUtil {
	
	public static String getCurrentUser(Model model) {
		CurrentUser currentUser =(CurrentUser) model.asMap().get("currentUser");
		String from = currentUser.getNickname();
		model.addAttribute("fromUser", from);
		return from;
	}

}
