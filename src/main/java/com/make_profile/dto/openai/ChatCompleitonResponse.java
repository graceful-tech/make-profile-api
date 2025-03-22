package com.make_profile.dto.openai;

import java.util.List;

public class ChatCompleitonResponse {

	private List<Choice> choices;

	public ChatCompleitonResponse() {
	}

	public ChatCompleitonResponse(List<Choice> choices) {
		this.choices = choices;
	}

	// Getters and Setters
	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public static class Choice {

		private int index;
		private ChatMessage message;

		public Choice() {
		}

		public Choice(int index, ChatMessage message) {
			this.index = index;
			this.message = message;
		}

		// Getters and Setters
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public ChatMessage getMessage() {
			return message;
		}

		public void setMessage(ChatMessage message) {
			this.message = message;
		}
	}

}
