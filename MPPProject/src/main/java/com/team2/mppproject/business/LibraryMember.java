package com.team2.mppproject.business;

import java.io.Serializable;
import java.util.ArrayList;

final public class LibraryMember extends Person implements Serializable {

    private String memberId;
    private CheckOutRecord checkOutRecord;

    public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
        super(fname, lname, tel, add);
        this.memberId = memberId;
        this.checkOutRecord = new CheckOutRecord(this, new ArrayList<>());
    }

    public CheckOutRecord getCheckOutRecord() {
        return checkOutRecord;
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName()
                + ", " + getTelephone() + " " + getAddress();
    }

    private static final long serialVersionUID = -2226197306790714013L;
}
