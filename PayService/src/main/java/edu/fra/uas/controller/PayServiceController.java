package edu.fra.uas.controller;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import org.springframework.hateoas.Link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.PayUser;
import edu.fra.uas.model.State;
import edu.fra.uas.service.PayUserException;
import edu.fra.uas.service.PayUserService;
import edu.fra.uas.service.dto.AccountResponseDTO;
import edu.fra.uas.service.dto.PayUserResponseDTO;
import edu.fra.uas.service.dto.TransferDTO;

@RestController
@RequestMapping(value = "/users")
public class PayServiceController {

	private PayUserService payUserService;
	
    @Autowired
    public PayServiceController(PayUserService payUserService) {
        this.payUserService = payUserService;
    }
	
    @RequestMapping(value = "/{userId}") //, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listPayUser(@PathVariable String userId) {
        PayUser payUser = payUserService.getPayUser(userId);
        PayUserResponseDTO payUserResponseDTO = new PayUserResponseDTO(payUser);
        
//        Link selfLink = linkTo(PayServiceController.class).slash(userId).withSelfRel();
//        Link accountLink = linkTo(methodOn(PayServiceController.class).listAccountBalance(userId)).withRel("account");
//        Link suspendLink = linkTo(methodOn(PayServiceController.class).changeUserToSuspendedState(userId, "suspended")).withRel("suspend");
//        payUserResponseDTO.add(selfLink);
//        payUserResponseDTO.add(accountLink);
//        payUserResponseDTO.add(suspendLink);
        
        return ResponseEntity.status(HttpStatus.OK).body(payUserResponseDTO);
    }

    @RequestMapping(value = "/{userId}/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAccountBalance(@PathVariable String userId) {
        State payUserState = payUserService.getState(userId);
        if (payUserState == State.available) {
            int balance = payUserService.getAccountBalanceByName(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseDTO("Account balance is " + balance));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AccountResponseDTO("transferNotAllowed"));
        }
    }

    @RequestMapping(value = "/{userId}/opened", method = RequestMethod.PUT)
    public ResponseEntity<?> openAccount(@PathVariable String userId) {
        payUserService.openAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseDTO("Account is now available"));
    }

    @RequestMapping(value = "/{userId}/payment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transfer(@PathVariable String userId, @RequestBody TransferDTO input) {
        String to = input.getTo();
        int amount = input.getAmount();
        String returnStatus = null;
        try {
            returnStatus = payUserService.transfer(userId, to, amount);
        } catch (PayUserException e) {
            e.printStackTrace();
        }

        if (returnStatus.equals("okay"))
            return ResponseEntity.status(HttpStatus.CREATED).body(new AccountResponseDTO("Transfer is successfull"));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AccountResponseDTO(returnStatus));
    }

    @RequestMapping(value = "/{userId}/deleted", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        payUserService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseDTO("User and account is now deleted"));
    }

    @RequestMapping(value = "/{userId}/suspended", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserToSuspendedState(@PathVariable String userId, @RequestBody String state) {
    	if (State.valueOf(state) == State.suspended) {       	
            payUserService.changeUserToSuspendedState(userId);
            return ResponseEntity.status(HttpStatus.OK).body(new AccountResponseDTO("suspended"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AccountResponseDTO("Incorrect state entered"));
    }

    
}
