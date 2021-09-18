package edu.fra.uas.security.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.fra.uas.common.CurrentUserUtil;
import edu.fra.uas.conversation.service.ConversationUserServiceImpl;
import edu.fra.uas.security.model.UserCreateForm;
import edu.fra.uas.security.service.dto.UserDTO;
import edu.fra.uas.security.service.user.UserService;
import edu.fra.uas.security.service.validator.UserCreateFormValidator;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    private UserService userService;
    
    private ConversationUserServiceImpl conversationUserService;
    
    private UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public UserController(UserService securityUserService, ConversationUserServiceImpl userService, UserCreateFormValidator userCreateFormValidator) {
        this.userService = securityUserService;
        this.conversationUserService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @InitBinder("myform")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @RequestMapping("/users")
    public String getUsersPage(Model model) {
        log.info("/users --> getting user page");
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @PreAuthorize("#id == principal.id or hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    public String getUserPage(@PathVariable Long id, Model model) {
        log.debug("/users/" + id + " --> getting user page for user= " + id);
        UserDTO userDTO = 
        		userService.getUserById(id);
        model.addAttribute("user", userDTO);
        model.addAttribute("fromUser", userDTO.getNickname());
        return "user";
    }
    
    //@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/managed", method = {RequestMethod.POST, RequestMethod.GET}) 
    public String getUserManagedPage(Model model) {
        log.debug("/users/managed --> getting user create form");
        model.addAttribute("myform", new UserCreateForm());
        model.addAttribute("users", userService.getAllUsers());
        CurrentUserUtil.getCurrentUser(model);
        return "user_create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("myform") UserCreateForm form, BindingResult bindingResult, Model model) {
        log.info("/users/create --> processing user create form= " + form + " bindingResult= " + bindingResult);
        model.addAttribute("users", userService.getAllUsers());
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getGlobalError().getDefaultMessage());
            return "user_create";
        }
        userService.create(form);
        conversationUserService.createConversationsUser(form);
        return "redirect:/users/managed";
    }

}
