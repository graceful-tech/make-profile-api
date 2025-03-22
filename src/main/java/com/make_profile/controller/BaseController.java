package com.make_profile.controller;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.make_profile.dto.MessageDto;
import com.make_profile.dto.ResponseDto;
import com.make_profile.service.MessageService;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

	@Autowired
	MessageService messageService;

	@Autowired
	HttpServletRequest httpRequest;

	public ResponseDto buildResponse(String code) {
		ResponseDto responseDto = new ResponseDto();
		MessageDto messageDto = messageService.getMessageByCode(code);

		if (Objects.nonNull(messageDto)) {
			responseDto.setCode(code);
			responseDto.setStatus(messageDto.getType());
			responseDto.setMessage(messageDto.getMessage());
		}
		return responseDto;
	}

	public ResponseDto buildResponse(String code, Long id) {
		ResponseDto responseDto = new ResponseDto();
		MessageDto messageDto = messageService.getMessageByCode(code);

		responseDto.setId(id);
		if (Objects.nonNull(messageDto)) {
			responseDto.setCode(code);
			responseDto.setStatus(messageDto.getType());
			responseDto.setMessage(messageDto.getMessage());
		}
		return responseDto;
	}

	public ResponseDto buildResponse(MethodArgumentNotValidException ex) {
		ResponseDto responseDto = new ResponseDto();

		String message = ex.getBindingResult().getAllErrors().stream()
				.map(error -> messageService.getMessageByCode(error.getDefaultMessage()).getMessage())
				.collect(Collectors.joining(", "));

		if (Objects.nonNull(message)) {
			responseDto.setStatus("Error");
			responseDto.setMessage(message);
		}
		return responseDto;
	}

//	public String getUserName() throws Exception {
//		String userName = null;
//		try {
//			userName = httpRequest.getHeader("username");
//
//			if (userName == null) {
//				throw new HurecomException("Unauthorized");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//		return userName;
//	}

}
