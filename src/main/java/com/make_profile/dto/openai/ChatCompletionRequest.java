package com.make_profile.dto.openai;

import java.util.ArrayList;
import java.util.List;

public class ChatCompletionRequest {

	private String model;
	private List<ChatMessage> messages;

	public ChatCompletionRequest(String model, String message) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new ChatMessage("user", message));
	}

	// Getters and Setters
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}

}
