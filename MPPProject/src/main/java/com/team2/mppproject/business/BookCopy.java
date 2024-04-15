package com.team2.mppproject.business;

import java.io.Serializable;

/**
 * Immutable class
 */
final public class BookCopy implements Serializable {

    private static final long serialVersionUID = -63976228084869815L;
    private Book book;
    private int copyNum;
    private boolean isAvailable;
    private CheckOutRecordEntry checkOutRecordEntry;

    BookCopy(Book book, int copyNum, boolean isAvailable) {
        this.book = book;
        this.copyNum = copyNum;
        this.isAvailable = isAvailable;
    }

    public CheckOutRecordEntry getCheckOutRecordEntry() {
        return checkOutRecordEntry;
    }

    void setCheckOutRecordEntry(CheckOutRecordEntry checkOutRecordEntry) {
        this.checkOutRecordEntry = checkOutRecordEntry;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCopyNum() {
        return copyNum;
    }

    public Book getBook() {
        return book;
    }

    public void changeAvailability() {
        isAvailable = !isAvailable;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) {
            return false;
        }
        if (!(ob instanceof BookCopy)) {
            return false;
        }
        BookCopy copy = (BookCopy) ob;
        return copy.book.getIsbn().equals(book.getIsbn()) && copy.copyNum == copyNum;
    }

}
