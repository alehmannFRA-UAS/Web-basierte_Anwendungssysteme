package edu.fra.uas.conversation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.conversation.service.ConversationUserService;
import edu.fra.uas.conversation.service.dto.ConversationDTO;
import edu.fra.uas.security.model.CurrentUser;
import edu.fra.uas.security.service.user.UserService;

@Controller
public class ConversationController {

    private static final Logger log = LoggerFactory.getLogger(ConversationController.class);

    private ConversationUserService conversationUserService;

    private UserService userService;

    private CurrentUser currentUser;

    @Autowired
    public ConversationController(ConversationUserService userService, UserService securityUserService, CurrentUser currentUser) {
        this.conversationUserService = userService;
        this.userService = securityUserService;
        this.currentUser = currentUser;
    }

    @RequestMapping(value = "/first", method = {RequestMethod.POST, RequestMethod.GET})
    public String firstPage(@RequestParam String email, @RequestParam String password, Model model) {
    	log.debug("/first --> log in with email " + email + " and password " + password);
        if (!userService.existsByEmail(email)) {
        	log.debug("/first --> email was wrong!");
            model.addAttribute("error","wrong login");
            return "login";
        }
        if (!password.equals(userService.getUserByEmail(email).get().getPassword())) {
        	log.debug("/first --> password was wrong!");
            model.addAttribute("error","wrong login");
            return "login";
        }
        currentUser.setUser(userService.getUserByEmail(email).get());
        String from = currentUser.getNickname();
        List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);
        model.addAttribute("fromUser", from);
        model.addAttribute("listAllChats", targetList);
        return "conversation";
    }

    @RequestMapping(value = "/newconversation", method = {RequestMethod.POST, RequestMethod.GET})
    public String newConversationPage(@RequestParam("nid") String to, Model model) {
    	log.debug("/newconversation --> new conversation to " + to);
    	String from = getCurrentUser(model);
        if (to.equals("")) {
            model.addAttribute("error","recipient empty");
        } else
        if ((!conversationUserService.existsNickname(to)) ) {
            model.addAttribute("error","the participant is unknown!!!");
        } else
            if (conversationUserService.conversationUserConversationsContainsKeyfindByNickname(from, to) || (to == " ")) {
                model.addAttribute("error","you already have a conversation with this participant");
            }
            else
                if (from.equals(to)) {
                    model.addAttribute("error","a conversation with yourself is not allowed");
                }
                else {
                   conversationUserService.newConversationFromTo(from, to);
                }
        List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);        
        model.addAttribute("listAllConversations", targetList);
        return "newconversation";
    }    

    @RequestMapping(value = "/start", method = {RequestMethod.POST, RequestMethod.GET})
    public String startPage(@RequestParam String fromUser, Model model) {
    	log.debug("GET /start --> list all conversations for " + fromUser);
    	String from = getCurrentUser(model);
        List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);
        model.addAttribute("listAllConversations", targetList);
        return "conversation";
    }

    @RequestMapping(value = "/deleteConversation", method = RequestMethod.POST)
    public String removeConversation(@RequestParam String to, Model model) {
    	String from = getCurrentUser(model);
    	log.debug("POST /deleteConversation --> delete conversations between " + from + " and " + to);
        conversationUserService.deleteConversationFromTo(from, to);
        return "redirect:start?fromUser=" + from;
    }

    private String getCurrentUser(Model model) {
        String from = currentUser.getUser().getNickname();
        model.addAttribute("fromUser", from);
        return from;
    }
    
}
