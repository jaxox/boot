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
    private String userName;

    public IndividualUserNode(String uid, String userName) {
        this.uid = uid;
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
