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

import edu.fra.uas.common.CurrentUserUtil;
import edu.fra.uas.conversation.service.ConversationUserService;
import edu.fra.uas.conversation.service.dto.ConversationDTO;

@Controller
public class ConversationController {

    private static final Logger log = LoggerFactory.getLogger(ConversationController.class);

    private ConversationUserService conversationUserService;

    @Autowired
    public ConversationController(ConversationUserService userService) {
        this.conversationUserService = userService;
    }

    @RequestMapping(value = "/first")
    public String firstPage(Model model) {    	
    	String from = CurrentUserUtil.getCurrentUser(model);
    	log.debug("/first --> log in from " + from);
        List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);
        model.addAttribute("fromUser", from);
        model.addAttribute("listAllConversations", targetList);
        return "conversation";
    }

    @RequestMapping(value = "/newconversation", method = {RequestMethod.POST, RequestMethod.GET})
    public String newConversationPage(@RequestParam("nid") String to, Model model) {
    	log.debug("/newconversation --> new conversation to " + to);
    	String from = CurrentUserUtil.getCurrentUser(model);
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

    @RequestMapping(value = "/deleteConversation", method = RequestMethod.POST)
    public String removeConversation(@RequestParam String to, Model model) {
    	String from = CurrentUserUtil.getCurrentUser(model);
    	log.debug("POST /deleteConversation --> delete conversations between " + from + " and " + to);
        conversationUserService.deleteConversationFromTo(from, to);
        return "redirect:first";
    }
    
}
