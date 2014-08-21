package com.boot.exception;
/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/7/13
 * Time: 4:02 PM
 */

public class UnauthorizedRequestException extends RuntimeException {

    public UnauthorizedRequestException(String message) {
        super(message);
    }
}