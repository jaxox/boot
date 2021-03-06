package com.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/7/13
 * Time: 4:02 PM
 */
@ResponseStatus( value = HttpStatus.BAD_REQUEST )
public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String message) {
        super(message);
    }
}