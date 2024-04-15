package com.team2.mppproject.useCases;

import com.team2.mppproject.business.Book;
import java.util.List;

public interface SearchBookUseCase {
	public Book searchBook(String isbn);

	public List<Book> getBookCollection();
}
