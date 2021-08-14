package edu.fra.uas.conversation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.conversation.model.Conversation;
import edu.fra.uas.user.model.User;
import edu.fra.uas.user.service.UserService;

@Controller
public class ConversationController {

    private static final Logger log = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/start")
    public String listingAllConversations(@RequestParam String from, Model model) {
    	log.debug("GET /start --> list all conversations for " + from);
        User userFrom = userService.getByName(from);
        List<Conversation> targetList = new ArrayList<>(userFrom.getConversations().values());
        model.addAttribute("fromUser", from);
        model.addAttribute("listAllConversations", targetList);
        return "conversation";
    }

    @RequestMapping(value = "/deleteConversation", method = RequestMethod.POST)
    public String removeConversation(@RequestParam String from, @RequestParam String to, Model model) {
    	log.debug("POST /deleteConversation --> delete conversations between " + from + " and " + to);
        model.addAttribute("fromUser", from);
        userService.deleteConversationFromTo(from, to);
        return "redirect:start?from=" + from;
    }

}
