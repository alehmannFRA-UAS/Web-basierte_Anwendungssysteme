package edu.fra.uas.message.service;

import edu.fra.uas.message.service.dto.PayActionResponseDTO;

public interface PaymentService {
	
    PayActionResponseDTO doPayAction(String from, String to, String content);
    
}