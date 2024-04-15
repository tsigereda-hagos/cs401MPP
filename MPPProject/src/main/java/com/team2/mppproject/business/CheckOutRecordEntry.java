package com.team2.mppproject.business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutRecordEntry implements Serializable {

    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;
    private CheckOutRecord checkoutRecord;

    public CheckOutRecordEntry(LocalDate checkoutDate, LocalDate dueDate, BookCopy bookCopy, CheckOutRecord checkoutRecord) {
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
        this.checkoutRecord = checkoutRecord;
        
        bookCopy.setCheckOutRecordEntry(this);
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public CheckOutRecord getCheckoutRecord() {
        return checkoutRecord;
    }

    void setCheckoutRecord(CheckOutRecord checkoutRecord) {
        this.checkoutRecord = checkoutRecord;
    }
}
