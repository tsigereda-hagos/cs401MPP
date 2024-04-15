package com.team2.mppproject.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    private LibraryMember member;
    private List<CheckOutRecordEntry> checkOutRecordEntries;

    public CheckOutRecord(LibraryMember member, List<CheckOutRecordEntry> checkOutRecordEntries) {
        this.member = member;
        this.checkOutRecordEntries = checkOutRecordEntries;
    }

    public LibraryMember getMember() {
        return member;
    }

    public List<CheckOutRecordEntry> getCheckOutRecordEntries() {
        return checkOutRecordEntries;
    }

    public void addCheckOutRecordEntry(CheckOutRecordEntry entry) {
        if (checkOutRecordEntries == null) {
            checkOutRecordEntries = new ArrayList<>();
        }
        
        entry.setCheckoutRecord(this);
        this.checkOutRecordEntries.add(entry);
    }
}
