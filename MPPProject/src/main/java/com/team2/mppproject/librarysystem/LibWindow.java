package com.team2.mppproject.librarysystem;

import com.team2.mppproject.exception.LoginException;

public interface LibWindow {
    void init() throws LoginException;

    boolean isInitialized();

    void isInitialized(boolean val);

    void setVisible(boolean b);
}

