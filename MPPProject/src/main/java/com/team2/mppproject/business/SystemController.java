package com.team2.mppproject.business;

import com.team2.mppproject.dataaccess.Auth;
import com.team2.mppproject.dataaccess.DataAccess;
import com.team2.mppproject.dataaccess.DataAccessFacade;
import com.team2.mppproject.dataaccess.User;
import com.team2.mppproject.exception.InvalidInputException;
import com.team2.mppproject.useCases.ControllerInterface;
import com.team2.mppproject.exception.LoginException;
import com.team2.mppproject.exception.NotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemController implements ControllerInterface {

    public static Auth currentAuth = null;

    public Auth login(String id, String password) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();
        return currentAuth;
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public List<String> allAuthorIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readAuthorMap().keySet());
        return retval;
    }

    @Override
    public Book searchBook(String isbn) throws NotFoundException {
        if (isbn == null || isbn.isEmpty()) {
            throw new InvalidInputException("ISBN is required.");
        }

        DataAccess da = new DataAccessFacade();

        Book book = da.searchBook(isbn);

        if (book == null) {
            throw new NotFoundException("Book not found.");
        }

        return da.searchBook(isbn);
    }
    
    

    @Override
    public BookCopy addBookCopy(String isbn) {
        Book book = searchBook(isbn);
        book.addCopy();

        DataAccess da = new DataAccessFacade();
        return da.addBookCopy(book);
    }

    @Override
    public List<BookCopy> getBookCopies(String isbn) {
        if (isbn != null && !isbn.isEmpty()) {
            searchBook(isbn);

            DataAccess da = new DataAccessFacade();
            return Arrays.asList(da.getBookCopies(isbn));
        } else {
            List<BookCopy> bookCopies = new ArrayList<>();

            DataAccess da = new DataAccessFacade();

            for (Map.Entry<String, Book> mapElement : da.readBooksMap().entrySet()) {
                Book book = mapElement.getValue();
                bookCopies.addAll(Arrays.asList(book.getCopies()));
            }

            return bookCopies;
        }
    }

    @Override
    public void addMember(String st, String city, String state, String zipCode, String fname, String lname,
            String tel) {
        if (fname.isEmpty()) {
            throw new InvalidInputException("Firstname is required.");
        }

        if (lname.isEmpty()) {
            throw new InvalidInputException("Lastname is required.");
        }

        if (st.isEmpty()) {
            throw new InvalidInputException("Street is required.");
        }

        if (city.isEmpty()) {
            throw new InvalidInputException("City is required.");
        }

        if (state.isEmpty()) {
            throw new InvalidInputException("State is required.");
        }

        if (zipCode.isEmpty()) {
            throw new InvalidInputException("Zip code is required.");
        }

        if (tel.isEmpty()) {
            throw new InvalidInputException("Telephone is required.");
        }

        Address address = new Address(st, city, state, zipCode);

        DataAccess da = new DataAccessFacade();
        String tempId = "100";
        String memberId = tempId + String.valueOf(da.readMemberMap().size() + 1);

        LibraryMember libraryMember = new LibraryMember(memberId, fname, lname, tel, address);

        da.saveNewMember(libraryMember);
    }

    @Override
    public void addBook(String isbn, String title, int maxCheckoutLength, int numOfCopies, List<String> authorIds) {
        if (isbn.isEmpty()) {
            throw new InvalidInputException("ISBN is required.");
        }

        if (title.isEmpty()) {
            throw new InvalidInputException("Title is required.");
        }

        if (maxCheckoutLength == 0) {
            throw new InvalidInputException("Maximum checkout length cannot be zero.");
        }

        if (numOfCopies == 0) {
            throw new InvalidInputException("Number of copies cannot be zero.");
        }

        if (authorIds.isEmpty()) {
            throw new InvalidInputException("Please choose atleast one author.");
        }

        DataAccess da = new DataAccessFacade();

        List<Author> authors = new ArrayList<>();
        HashMap<String, Author> authorMap = da.readAuthorMap();

        for (String authorId : authorIds) {
            authors.add(authorMap.get(authorId));
        }

        Book book = new Book(isbn, title, maxCheckoutLength, authors);

        for (int i = 0; i < numOfCopies; i++) {
            book.addCopy();
        }

        da.saveNewBook(book);
    }
    
    public void logout() {
        this.currentAuth = null;
    }
}
