package com.team2.mppproject.useCases;

import com.team2.mppproject.business.Book;
import com.team2.mppproject.business.BookCopy;
import com.team2.mppproject.dataaccess.Auth;
import com.team2.mppproject.exception.LoginException;
import java.util.List;

public interface ControllerInterface {
    public Auth login(String id, String password) throws LoginException;
    
    public void logout();

    public List<String> allMemberIds();

    public List<String> allBookIds();
    
    public List<String> allAuthorIds();

    public Book searchBook(String isbn);

    public BookCopy addBookCopy(String isbn);

    public List<BookCopy> getBookCopies(String isbn);

    public void addMember(String st, String city, String state, String zipCode, String fname, String lname, String tel);
    
    public void addBook(String isbn, String title, int maxCheckoutLength, int numOfCopies, List<String> authorIds);
}
