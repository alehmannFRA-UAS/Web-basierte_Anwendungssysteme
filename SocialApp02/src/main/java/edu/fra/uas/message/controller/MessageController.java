package edu.fra.uas.message.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.message.model.Message;
import edu.fra.uas.message.service.MessageService;

@Controller
public class MessageController {

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);

  @Autowired
  private MessageService messageService;

  @RequestMapping("/messages")
  public String listAllMessages(@RequestParam String from, @RequestParam String to, Model model) {
	  log.debug("GET /messages --> list all messages for " + from + " and " + to);
      List<Message> list = messageService.listAllMessagesFromTo(from, to);
      model.addAttribute("listAllMessages", list);
      model.addAttribute("toUser", to);
      model.addAttribute("fromUser", from);
      model.addAttribute("currentUser", from);
      return "messaging";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addMessage(@RequestParam String from, @RequestParam String to, @RequestParam String content, Model model) {
	  log.debug("POST /add --> add message from " + from + " to " + to + " with content: " + content);
      model.addAttribute("fromUser", from);
      model.addAttribute("currentUser", from);
      List<Message> list = messageService.listAllMessagesFromTo(from, to);
      model.addAttribute("listAllMessages", list);
      messageService.addMessage(from, to, content);      
      return "redirect:messages?from="+ from + "&to=" + to;
  }

}
