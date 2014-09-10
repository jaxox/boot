package com.boot.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/4/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactNode {
    String name;
    String data;
    Set<String> onShareProfileList = new HashSet<String>();

}
