package com.team2.mppproject.dataaccess;

import com.team2.mppproject.business.Author;
import com.team2.mppproject.business.Book;
import com.team2.mppproject.business.BookCopy;
import com.team2.mppproject.business.CheckOutRecord;
import com.team2.mppproject.business.LibraryMember;
import java.util.HashMap;

public interface DataAccess {

    public HashMap<String, Book> readBooksMap();

    public HashMap<String, User> readUserMap();
    
    public HashMap<String, Author> readAuthorMap();

    public HashMap<String, LibraryMember> readMemberMap();

    public void saveNewMember(LibraryMember member);

    public Book searchBook(String isbn);

    public BookCopy addBookCopy(Book book);

    public BookCopy[] getBookCopies(String isbn);

    public void updateBookHM(HashMap<String, Book> hmBooks);
    
    public void saveNewBook(Book book);
    
    public void updateMember(LibraryMember member);
}
