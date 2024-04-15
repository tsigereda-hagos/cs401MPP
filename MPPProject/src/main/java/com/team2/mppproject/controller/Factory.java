package com.team2.mppproject.controller;

import com.team2.mppproject.useCases.CheckMemberUseCase;
import com.team2.mppproject.useCases.SearchBookUseCase;


public class Factory {
    public static SearchBookUseCase createSearchBookUseCase() {
        SearchBookUseCase useCase = new BookController();
        return useCase;
    }
    public static CheckMemberUseCase createCheckMemberUseCase() {
        CheckMemberUseCase useCase = new LibraryMemberController();
        return useCase;
    }
}
