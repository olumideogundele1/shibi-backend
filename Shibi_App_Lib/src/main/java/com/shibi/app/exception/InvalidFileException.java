package com.shibi.app.exception;

import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * Created by User on 04/07/2018.
 */
public class InvalidFileException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidFileException(String message){
        super(message);
    }
}
