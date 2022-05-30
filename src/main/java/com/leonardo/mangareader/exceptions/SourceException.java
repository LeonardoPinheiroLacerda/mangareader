package com.leonardo.mangareader.exceptions;

public class SourceException extends RuntimeException {
    
    public SourceException(String msg){
        super(msg);
    }

    public SourceException(String msg, Throwable cause){
        super(msg, cause);
    }

}
