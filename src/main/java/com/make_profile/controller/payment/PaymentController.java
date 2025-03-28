package com.make_profile.controller.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.make_profile.dto.payment.PaymentDto;
import com.make_profile.dto.payment.PaymentOrderDto;
import com.make_profile.service.impl.payment.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	PaymentService paymentService;

	public ResponseEntity<?> generatePayment(@RequestBody PaymentOrderDto paymentOrderDto) {

		logger.debug("Controller :: generatePayment :: Entered");

		PaymentOrderDto generatePayment = paymentService.generatePayment(paymentOrderDto);

		logger.debug("Controller :: generatePayment :: Entered");
		return new ResponseEntity<>(generatePayment, HttpStatus.OK);

	}

	@PostMapping("/save")
	public ResponseEntity<?> savePayment(@RequestBody PaymentDto paymentDto) throws Exception {
		logger.debug("Controller :: savePayment :: Entered");

		boolean status = paymentService.savePayment(paymentDto);

		logger.debug("Controller :: savePayment :: Exited");

		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
