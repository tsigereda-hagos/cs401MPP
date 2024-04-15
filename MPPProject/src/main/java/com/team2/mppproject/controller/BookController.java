package com.team2.mppproject.controller;

import com.team2.mppproject.business.Book;
import com.team2.mppproject.dataaccess.DataAccess;
import com.team2.mppproject.dataaccess.DataAccessFacade;
import com.team2.mppproject.useCases.SearchBookUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookController implements SearchBookUseCase {

	BookController() {
	}

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		return map.get(isbn);
	}

	@Override
	public List<Book> getBookCollection() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();

		List<Book> books = new ArrayList<>(map.values());
		return books;
	}
}
