package com.sudokutorial.model.event;

import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String token;
	private String url;
	
	public OnRegistrationCompleteEvent(Object source) {
		super(source);
	}

	public OnRegistrationCompleteEvent(String email, String token, String url) {
		super(email);
		
		this.email = email;
		this.token = token;
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "OnRegistrationCompleteEvent [email=" + email + ", token=" + token + ", url=" + url + "]";
	}
	
}
