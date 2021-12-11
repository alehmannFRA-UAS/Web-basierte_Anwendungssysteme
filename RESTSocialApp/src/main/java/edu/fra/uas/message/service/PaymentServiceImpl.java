package edu.fra.uas.message.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.message.service.dto.AccountResponseDTO;
import edu.fra.uas.message.service.dto.PayActionResponseDTO;
import edu.fra.uas.message.service.dto.TransferDTO;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${paymentservice.url}")
    String paymentUrl;

    @Value("${paymentservice.plainCreds}")
    String plainCreds;

    @Override
    public PayActionResponseDTO doPayAction(String from, String to, String content) {
        String token1 ="", token2 ="", token3 ="";
        PayActionResponseDTO payActionResponse =  new PayActionResponseDTO().payment(false).setDescription("unknown problem -> transfer not successful");

        String[] tokens = content.split("\\s+");
        if (tokens.length == 0) 
        	return payActionResponse.setDescription("wrong syntax");
        if (tokens.length >= 4) 
        	return payActionResponse.setDescription("wrong syntax - input too long / too many words");

        for (int i=0; i<tokens.length; i++) {
            if (i == 0) token1 = tokens[0];
            if (i == 1) token2 = tokens[1];
            if (i == 2) token3 = tokens[2];
        }

        if ((token1.equals("get") || token1.equals("delete") || token1.equals("open") || token1.equals("suspend")) && (token2.equals("")) && (token3.equals(""))) {
            return paymentAccountCommunication(token1, token2, token3, from, payActionResponse);
        }

        if ((token1.equals("transfer")) && (!token2.equals(from))) {
            try {
                log.debug("token3 value: " + Integer.valueOf(token3));
            } catch (Exception e) {
                return payActionResponse.setDescription("wrong syntax (amount is not a number)");
            }
            return paymentAccountCommunication(token1, token2, token3, from, payActionResponse);
        }

        return payActionResponse.setDescription("input is incorrect");
    }

    private PayActionResponseDTO paymentAccountCommunication(String token1, String token2, String token3, String from, PayActionResponseDTO payActionResponse) {

        String uriReturn;
        ResponseEntity<?> response = null;
        RestTemplate restTemplate = new RestTemplate();
//        String plainCreds = plainCredsUser;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
	        switch (token1) {
	            case "get":
	                uriReturn = paymentUrl + from + "/account";
	                response = restTemplate.exchange(uriReturn, HttpMethod.GET, request, AccountResponseDTO.class);
	                break;
	            case "delete":
	                uriReturn = paymentUrl + from + "/deleted";
	                response = restTemplate.exchange(uriReturn, HttpMethod.DELETE, request, AccountResponseDTO.class);
	                break;
	            case "open":
	                uriReturn = paymentUrl + from + "/opened";
	                response = restTemplate.exchange(uriReturn, HttpMethod.PUT, request, AccountResponseDTO.class);
	                break;
	            case "suspend":
	            	uriReturn = paymentUrl + from + "/suspended";
	                headers.setContentType(MediaType.APPLICATION_JSON);
	                HttpEntity<String> requestPut = new HttpEntity<>("suspended", headers);
	                response = restTemplate.exchange(uriReturn, HttpMethod.PUT, requestPut, AccountResponseDTO.class);
	                break;
	            case "transfer":
	                headers.setContentType(MediaType.APPLICATION_JSON);
	                TransferDTO transferDTO = new TransferDTO(token2, Integer.valueOf(token3));
	                HttpEntity<TransferDTO> requestPost = new HttpEntity<>(transferDTO, headers);
	                uriReturn = paymentUrl + from + "/payment";
	                response = restTemplate.exchange(uriReturn, HttpMethod.POST, requestPost, AccountResponseDTO.class);
	                break;
	            default:
	                return payActionResponse.setDescription("wrong syntax - unknown command");
	        }

        } catch(ResourceAccessException e){
            response = new ResponseEntity<Object>(new AccountResponseDTO(token1 + " " + "not successful :: payment service not available?"), HttpStatus.OK);
        }

        catch(Exception e){
            response = new ResponseEntity<Object>(new AccountResponseDTO(token1 + " " + "not successful :: possibly recipient unknown"), HttpStatus.OK);
        }

        AccountResponseDTO accountResponse = (AccountResponseDTO) response.getBody();
        payActionResponse.setDescription(accountResponse.getCode());

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            payActionResponse.payment(true);
        } else {
            payActionResponse.payment(false);
        }
        return payActionResponse;
    }

}