package com.make_profile.service.impl.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.make_profile.dto.master.CreditsDto;
import com.make_profile.dto.payment.PaymentDto;
import com.make_profile.dto.payment.PaymentOrderDto;
import com.make_profile.entity.common.EnvironmentEntity;
import com.make_profile.entity.common.payment.PaymentOrderEntity;
import com.make_profile.repository.common.EnvironmentRepository;
import com.make_profile.repository.common.payment.PaymentOrderRepository;
import com.make_profile.service.master.CreditsService;
import com.make_profile.service.payment.PaymentService;
import com.make_profile.utility.CommonConstants;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	PaymentOrderRepository paymentOrderRepository;

	@Autowired
	EnvironmentRepository environmentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CreditsService creditsService;

	private String razorPayKey;

	private String razorPaySecret;

	@PostConstruct
	private void setRazorPayConfiguration() {
		List<EnvironmentEntity> entities = environmentRepository.getRazorPayConfiguration();

		entities.forEach(entity -> {
			switch (entity.getEnvironmentKey()) {
			case CommonConstants.RAZOR_PAY_KEY: {
				razorPayKey = entity.getEnvironmentValue();
				break;
			}
			case CommonConstants.RAZOR_PAY_SECRET: {
				razorPaySecret = entity.getEnvironmentValue();
				break;
			}
			default:
				break;
			}
		});
	}

	@Override
	public PaymentOrderDto generatePayment(PaymentOrderDto paymentOrderDto) {
		logger.debug("Service :: generatePayment :: Entered");

		PaymentOrderDto paymentOrder = null;

		try {

			RazorpayClient razorPay = new RazorpayClient(razorPayKey, razorPaySecret);
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", paymentOrderDto.getAmount());
			orderRequest.put("currency", "INR");
			Order order = razorPay.orders.create(orderRequest);

			PaymentOrderEntity paymentOrderEntity = new PaymentOrderEntity();
			paymentOrderEntity.setOrderId(order.get("id"));
			paymentOrderEntity.setAmount(order.get("amount"));
			paymentOrderEntity.setCurrency(order.get("currency"));
			paymentOrderEntity.setStatus(order.get("status"));
			paymentOrderEntity.setCreatedDate(LocalDateTime.now());
			paymentOrderEntity.setTransactionId(generateTransactionId());
			// paymentOrderEntity.setCandidateId(paymentOrderDto.getCandidateId());
			paymentOrderEntity.setUserId(paymentOrderDto.getUserId());

			PaymentOrderEntity savedEntity = paymentOrderRepository.save(paymentOrderEntity);
			paymentOrder = modelMapper.map(savedEntity, PaymentOrderDto.class);

		} catch (Exception e) {
			logger.debug("Service :: generatePayment :: Exception :: " + e);
		}
		logger.debug("Service :: generatePayment :: Exited");
		return paymentOrder;
	}

	private String generateTransactionId() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return localDate.format(formatter);
	}

	@Override
	public boolean savePayment(PaymentDto paymentDto) {
		logger.debug("Service :: savePayment :: Entered");
		boolean status = false;
		try {
			if (paymentDto.getPaymentStatus().equalsIgnoreCase("Completed")) {

				PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.getPaymentOrder(paymentDto.getOrderId());

				paymentOrderEntity.setStatus("completed");
				paymentOrderRepository.save(paymentOrderEntity);

				addPaymentCredits(paymentDto);

				status = true;

			} else if (paymentDto.getPaymentStatus().equalsIgnoreCase("Failed")) {
				PaymentOrderEntity paymentOrderEntity = paymentOrderRepository.getPaymentOrder(paymentDto.getOrderId());
				paymentOrderEntity.setStatus("failed");
				paymentOrderRepository.save(paymentOrderEntity);
				status = false;
			}

		} catch (Exception e) {
			logger.debug("Service :: savePayment :: Exception :: " + e);
		}
		logger.debug("Service :: savePayment :: Exited");
		return status;
	}

	public boolean addPaymentCredits(PaymentDto paymentDto) {
		logger.debug("Service :: addPaymentCredits :: Entered");

		boolean status = false;
		CreditsDto creditsDto = new CreditsDto();
		try {

			creditsDto.setUserId(paymentDto.getUserId());
			creditsDto.setCreditAvailable(10.00);
			creditsDto.setPaymentDate(LocalDate.now());
			
			boolean addCredits = creditsService.addCredits(creditsDto);
		}

		catch (Exception e) {
			logger.debug("Service :: addPaymentCredits :: Exception :: " + e);
		}
		logger.debug("Service :: addPaymentCredits :: Exited");
		return status;

	}

}
