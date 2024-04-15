/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.team2.mppproject.exception;

/**
 *
 * @author battushigtsogtbaatar
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String msg) {
        super(msg);
    }

    public InvalidInputException(Throwable t) {
        super(t);
    }
    
}
