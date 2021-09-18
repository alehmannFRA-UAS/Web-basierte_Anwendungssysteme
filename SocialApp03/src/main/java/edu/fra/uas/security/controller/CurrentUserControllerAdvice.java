package edu.fra.uas.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import edu.fra.uas.security.model.CurrentUser;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    @Autowired 
    CurrentUser currentUser;

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser() {
        return currentUser;
    }

}
