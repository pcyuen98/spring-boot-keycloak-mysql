package com.wcc.exceptions;

public class FileNameTooLongException extends RuntimeException {
    public FileNameTooLongException(String errorMessage) {
        super(errorMessage);
    }
}
