package edu.fra.uas.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.fra.uas.api.model.Message;
import edu.fra.uas.api.model.ToUser;
import edu.fra.uas.api.model.UserCreate;
import edu.fra.uas.common.CurrentUserUtil;
import edu.fra.uas.common.Partition;
import edu.fra.uas.conversation.service.ConversationUserService;
import edu.fra.uas.conversation.service.dto.ConversationDTO;
import edu.fra.uas.message.service.MessageService;
import edu.fra.uas.message.service.dto.MessageDTO;
import edu.fra.uas.security.service.dto.UserDTO;
import edu.fra.uas.security.service.user.UserService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	
	private static final int MAXCONVERSATIONS = 5;
	
    private ConversationUserService conversationUserService;
    
    private MessageService messageService;
    
    private UserService adminUserService;

    @Autowired
    public ApiController(ConversationUserService userService, MessageService messageService, UserService adminUserService) {
        this.conversationUserService = userService;
        this.messageService = messageService;
        this.adminUserService = adminUserService;
    }

	@RequestMapping(value = "/conversations", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CollectionModel<ConversationDTO>> getConversations(@RequestParam(required = false) String page, Model model) {
		String from = CurrentUserUtil.getCurrentUser(model);
		log.debug("API GET /conversations --> list conversations for " + from);
        List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);
        if(targetList.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if(targetList.size() > MAXCONVERSATIONS && page == null) {
        	Link first = linkTo(methodOn(ApiController.class).getConversations("0", model)).withRel(IanaLinkRelations.FIRST);
        	Link next = linkTo(methodOn(ApiController.class).getConversations("1", model)).withRel(IanaLinkRelations.NEXT);
        	
        	int size = targetList.size();
        	int pages = Math.round(size/MAXCONVERSATIONS);
        	
        	Link last = linkTo(methodOn(ApiController.class).getConversations("" + pages, model)).withRel(IanaLinkRelations.LAST);
        	CollectionModel<ConversationDTO> result = CollectionModel.of(targetList.subList(0, MAXCONVERSATIONS));
        	result.add(first);
        	result.add(next);
        	result.add(last);
        	for(ConversationDTO conversationDTO : result) {
        		Link selfLink = linkTo(ApiController.class).slash("/conversations/" + conversationDTO.getConversationWith()).withSelfRel();
        		conversationDTO.add(selfLink);
        	}
        	return new ResponseEntity<>(result, HttpStatus.PARTIAL_CONTENT);
        } else if(page != null) {
        	int range = Integer.parseInt(page);
        	Partition<ConversationDTO> partition = Partition.ofSize(targetList, MAXCONVERSATIONS);
        	CollectionModel<ConversationDTO> result = null;
        	Link link = linkTo(methodOn(ApiController.class).getConversations(page, model)).withSelfRel();
        	try {
        		result = CollectionModel.of(partition.get(range), link);
            	for(ConversationDTO conversationDTO : result) {
            		Link selfLink = linkTo(ApiController.class).slash("/conversations/" + conversationDTO.getConversationWith()).withSelfRel();
            		conversationDTO.add(selfLink);
            	}
        	} catch (IndexOutOfBoundsException e) {
        		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
        	return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
        	Link link = linkTo(methodOn(ApiController.class).getConversations(page, model)).withSelfRel();
        	CollectionModel<ConversationDTO> result = CollectionModel.of(targetList, link);
        	for(ConversationDTO conversationDTO : result) {
        		Link selfLink = linkTo(ApiController.class).slash("/conversations/" + conversationDTO.getConversationWith()).withSelfRel();
        		conversationDTO.add(selfLink);
        	}
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/conversations/{to}",
			method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Object> deleteConversation(@PathVariable("to") String to, Model model) {
    	String from = CurrentUserUtil.getCurrentUser(model);
    	log.debug("API DELETE /conversations --> delete conversations between " + from + " and " + to);
        boolean result = conversationUserService.deleteConversationFromTo(from, to);
        log.debug("deleted? " + result);
        if(result) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@RequestMapping(value = "/conversations",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addConversation(@RequestBody ToUser to, Model model) {    	
  		String from = CurrentUserUtil.getCurrentUser(model);
       	log.debug("API POST /conversations --> add conversation between " + from + " and " + to.getName());
        if (to.getName().equals("")) {
            log.debug("recipient empty");
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: recipient empty\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
        } else if ((!conversationUserService.existsNickname(to.getName())) ) {
         	log.debug("the participant is unknown!!!");
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: participant is unknown\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
        } else if (conversationUserService.conversationUserConversationsContainsKeyfindByNickname(from, to.getName()) || (to.getName() == " ")) {
          	log.debug("you already have a conversation with this participant");
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: you already have a conversation with this participant\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
        } else if (from.equals(to.getName())) {
           	log.debug("a conversation with yourself is not allowed");
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: a conversation with yourself is not allowed\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
        } else {
           	conversationUserService.newConversationFromTo(from, to.getName());
           	List<ConversationDTO> targetList = conversationUserService.getAllConversationsFrom(from);
           	ConversationDTO conversation = null;
           	for(ConversationDTO conversationDTO : targetList) {
           		if(conversationDTO.getConversationWith().equals(to.getName())) {
           			Link selfLink = linkTo(ApiController.class).slash("/conversations/" + conversationDTO.getConversationWith()).withSelfRel();
           			conversationDTO.add(selfLink);
           			conversation = conversationDTO;
           		}
           	}
           	// Location Header in HTTP wird hinzugefuegt
           	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{to}").buildAndExpand(to.getName()).toUri();
           	return ResponseEntity.created(uri).body(conversation.toString());
        }
	}
	
	@RequestMapping(value = "/conversations/{to}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CollectionModel<Message>>	getMessagesOverview(@PathVariable("to") String to, Model model) {
		String from = CurrentUserUtil.getCurrentUser(model);
		log.debug("API GET /messages --> list messages as overview for " + from);
	    List<MessageDTO> list = messageService.listAllMessagesFromTo(from, to);
        if(list.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        	List<Message> overview = new ArrayList<Message>();
        	for(MessageDTO messageDTO : list) {
        		Message message = new Message();
        		message.setContent(messageDTO.getContent());
        		overview.add(message);
        	}        	
        	Link link = linkTo(methodOn(ApiController.class).getMessagesOverview(to, model)).withSelfRel();
        	CollectionModel<Message> result = CollectionModel.of(overview, link);
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/conversations/{to}/messages",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CollectionModel<MessageDTO>>	getMessages(@PathVariable("to") String to, Model model) {
		String from = CurrentUserUtil.getCurrentUser(model);
		log.debug("API GET /messages --> list messages for " + from);
	    List<MessageDTO> list = messageService.listAllMessagesFromTo(from, to);
        if(list.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        	Link link = linkTo(methodOn(ApiController.class).getMessages(to, model)).withSelfRel();
        	CollectionModel<MessageDTO> result = CollectionModel.of(list, link);
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
	}
	
	@RequestMapping(value = "/conversations/{to}/messages",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addMessage(@PathVariable("to") String to, @RequestBody Message message, Model model) {
		String from = CurrentUserUtil.getCurrentUser(model);
		log.debug("API POST /messages --> add message from " + from + " to " + to + " with content: " + message.getContent());
		if(to.isEmpty()) {
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: recipient empty\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
		} else if(to.isBlank()) {
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: recipient blank\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
		} else if(message.getContent().isEmpty()) {
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: message is empty\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
		} else if(message.getContent().isBlank()) {
            String responseValue = "{\"code\": 400,\"message\": \"Invalid json message: message is blank\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.BAD_REQUEST);
		} else {
			messageService.addMessage(from, to, message.getContent());
   			Link selfLink = linkTo(ApiController.class).slash("/conversations/" + to + "/messages").withSelfRel();
   			message.add(selfLink);
           	// Location Header in HTTP wird hinzugefuegt
           	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
           	return ResponseEntity.created(uri).body(message.toString());
		}	    
	}
	
    @RequestMapping(value = "/users",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getUsers() {
    	log.info("API GET /users --> list users");
   		Collection<UserDTO> collection = adminUserService.getAllUsers();
       	return new ResponseEntity<>(collection, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/users/{id}", 
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getUserId(@PathVariable Long id) {
        log.debug("API GET /users/" + id + " --> getting user= " + id);
        try {
        	UserDTO userDTO = adminUserService.getUserById(id);
        	
        	Link selfLink = linkTo(ApiController.class).slash("users/"+id).withSelfRel();
        	userDTO.add(selfLink);
        	
        	return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			String responseValue = "{\"code\": 404,\"message\": \"User ID not existing\"}";
            return new ResponseEntity<>(responseValue, HttpStatus.NOT_FOUND);
		}
    }
    
    @RequestMapping(value = "/users",
    		method = RequestMethod.POST,
    		consumes = MediaType.APPLICATION_JSON_VALUE,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> handleUserCreateForm(@Valid @RequestBody UserCreate userCreate) {
        log.info("/users/create --> processing user create form= " + userCreate);
        adminUserService.create(userCreate);
        conversationUserService.createConversationsUser(userCreate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}