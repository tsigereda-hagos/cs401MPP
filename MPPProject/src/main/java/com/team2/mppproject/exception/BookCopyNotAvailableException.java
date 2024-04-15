package com.team2.mppproject.exception;

import java.io.Serializable;

public class BookCopyNotAvailableException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public BookCopyNotAvailableException(String bookId) {
		super("BookId " + bookId + " copy is not available");
	}

}
