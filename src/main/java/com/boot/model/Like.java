package com.boot.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

/**
 * Created by jason on 12/14/14.
 */
public class Like {

    private String userId;
    //unlikely user change their name and prevent doing another query
    private String userName;
    //TODO: will createdDate work for embedded variable????
    @CreatedDate
    private DateTime creationTime;

    public Like(String userId, String userName){
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }
}
