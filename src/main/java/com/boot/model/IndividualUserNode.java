package com.boot.model;

/**
 * User: jyu
 * Date: 9/22/14
 */

/**
 * It is used by the SocialIdea to sharing with the list of individual user.
 */
public class IndividualUserNode {
    private String uid;
    private String username;

    public IndividualUserNode(String uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
