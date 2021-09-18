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

import edu.fra.uas.common.CurrentUserUtil;
import edu.fra.uas.message.service.MessageService;
import edu.fra.uas.message.service.dto.MessageDTO;

@Controller
public class MessageController {

  private static final Logger log = LoggerFactory.getLogger(MessageController.class);

  private MessageService messageService;
  
  @Autowired
  public MessageController(MessageService messageService) {
      this.messageService = messageService;
  }

  @RequestMapping("/messages")
  public String listAllMessages(@RequestParam String to, Model model) {	  
	  String from = CurrentUserUtil.getCurrentUser(model);
	  log.debug("GET /messages --> list all messages for " + from + " and " + to);	  
      List<MessageDTO> list = messageService.listAllMessagesFromTo(from, to);
      model.addAttribute("listAllMessages", list);
      model.addAttribute("toUser", to);
      return "messaging";
  }

  @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
  public String addMessage(@RequestParam String to, @RequestParam String content, Model model) {
	  String from = CurrentUserUtil.getCurrentUser(model);
	  log.debug("POST /add --> add message from " + from + " to " + to + " with content: " + content);
      messageService.addMessage(from, to, content);      
      return "redirect:messages?to=" + to;
  }

}
