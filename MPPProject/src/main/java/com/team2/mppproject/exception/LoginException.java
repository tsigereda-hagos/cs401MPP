package com.team2.mppproject.exception;

import java.io.Serializable;

public class LoginException extends RuntimeException implements Serializable {

	public LoginException() {
		super();
	}
	public LoginException(String msg) {
		super(msg);
	}
	public LoginException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 8978723266036027364L;
	
}
