package com.team2.mppproject.controller;

import com.team2.mppproject.business.LibraryMember;
import com.team2.mppproject.dataaccess.DataAccess;
import com.team2.mppproject.dataaccess.DataAccessFacade;
import com.team2.mppproject.useCases.CheckMemberUseCase;
import java.util.HashMap;

public class LibraryMemberController implements CheckMemberUseCase {

    LibraryMemberController() {
    }

    @Override
    public boolean checkMember(String memberId) {
        if (getMember(memberId) != null) {
            return true;
        }

        return false;
    }

    @Override
    public LibraryMember getMember(String memberId) {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> map = da.readMemberMap();
        return map.get(memberId);
    }

}
