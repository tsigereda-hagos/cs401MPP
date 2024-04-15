package com.team2.mppproject.exception;

import java.io.Serializable;

public class BookNotFoundException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String bookId) {
		super("BookId is not found");
	}

}
