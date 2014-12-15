package com.boot.model;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jason on 12/14/14.
 */
public class SocialIdeaItem {

    // idea's name / location's name
    private String name;
    // idea's description / location's address
    private String description;
    //who likes the idea
    private List<Like> likes = new LinkedList<Like>();
    private DateTime creationTime;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addLike(String loggedInUserId, String username) {
        likes.add(new Like(loggedInUserId,username));
    }
}
