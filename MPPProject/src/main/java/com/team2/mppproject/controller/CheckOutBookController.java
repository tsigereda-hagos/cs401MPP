package com.team2.mppproject.controller;

import com.team2.mppproject.business.Book;
import com.team2.mppproject.business.BookCopy;
import com.team2.mppproject.business.CheckOutRecordEntry;
import com.team2.mppproject.business.LibraryMember;
import com.team2.mppproject.dataaccess.DataAccess;
import com.team2.mppproject.dataaccess.DataAccessFacade;
import com.team2.mppproject.useCases.CheckMemberUseCase;
import com.team2.mppproject.useCases.SearchBookUseCase;
import com.team2.mppproject.exception.BookCopyNotAvailableException;
import com.team2.mppproject.exception.BookNotFoundException;
import com.team2.mppproject.exception.MemberNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class CheckOutBookController {

    private SearchBookUseCase searchBookUseCase;
    private CheckMemberUseCase checkMember;

    public CheckOutBookController() {
        searchBookUseCase = Factory.createSearchBookUseCase();
        checkMember = Factory.createCheckMemberUseCase();
    }

    public void checkOutBook(String memberId, String bookId) {
        DataAccess da = new DataAccessFacade();

        Book book = searchBookUseCase.searchBook(bookId);

        if (book == null) {
            throw new BookNotFoundException("Book not found");
        }

        if (!checkMember.checkMember(memberId)) {
            throw new MemberNotFoundException("Member not found");
        }

        LibraryMember member = checkMember.getMember(memberId);

        // Check Book Available
        BookCopy bookCopy = book.getNextAvailableCopy();

        if (bookCopy == null) {
            throw new BookCopyNotAvailableException(book.getIsbn());
        }

        bookCopy.changeAvailability();

        LocalDate dueDate = LocalDate.now().plusDays(bookCopy.getBook().getMaxCheckoutLength());
        LocalDate checkOutDate = LocalDate.now();
        CheckOutRecordEntry checkOutEntry = new CheckOutRecordEntry(checkOutDate, dueDate, bookCopy, member.getCheckOutRecord());

        member.getCheckOutRecord().addCheckOutRecordEntry(checkOutEntry);
        book.updateCopies(bookCopy);

        HashMap<String, Book> hmBooks = da.readBooksMap();
        hmBooks.put(book.getIsbn(), book);

        da.updateBookHM(hmBooks);
        da.updateMember(member);
    }

    public List<CheckOutRecordEntry> getCheckOutEntries(String memberId) {
        LibraryMember member = checkMember.getMember(memberId);
        return member.getCheckOutRecord().getCheckOutRecordEntries();
    }

}
