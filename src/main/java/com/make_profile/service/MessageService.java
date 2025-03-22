package com.make_profile.service;

import com.make_profile.dto.MessageDto;

public interface MessageService {

	MessageDto getMessageByCode(String code);
}
