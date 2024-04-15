package com.team2.mppproject.exception;

import java.io.Serializable;

public class MemberNotFoundException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public MemberNotFoundException(String memberId) {
		super("MemberId " + memberId + " is not found");
	}

}