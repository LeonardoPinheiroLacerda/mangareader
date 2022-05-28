package com.leonardo.mangareader.exceptions;

public class DataIntegrityException extends RuntimeException{
    
    public DataIntegrityException (String msg, Throwable cause){
        super(msg, cause);
    }

}
