package com.boot.util;

import com.boot.exception.ObjectNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/10/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class RestPreconditions {
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new ObjectNotFoundException("Not found");
        }
        return resource;
    }
}