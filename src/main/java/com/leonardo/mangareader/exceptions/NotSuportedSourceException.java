package com.leonardo.mangareader.exceptions;

public class NotSuportedSourceException extends RuntimeException {
    
    public NotSuportedSourceException(String msg){
        super(msg);
    }

    public NotSuportedSourceException(String msg, Throwable cause){
        super(msg, cause);
    }

}
