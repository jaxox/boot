package com.boot.util;

import org.springframework.http.HttpHeaders;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/8/13
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpHeaderUtils {

    static public HttpHeaders getHeader4Json(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return headers;
    }
}
