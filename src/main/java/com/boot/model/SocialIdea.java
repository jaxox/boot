package com.boot.model;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: jyu on 9/4/14
 *
 */
public class SocialIdea extends AbstractDocument {

    @NotNull
    @NumberFormat
    private String creatorId;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;

    /**Embedded documents**/
    //Map< [NameOfIdeas] , Map< [UserId who likes the idea] , [UserName] >>
    @Size(min = 1, max = 10)
    private Map<String,Map<String,String >> ideas = new LinkedHashMap<String,Map<String,String >>();

    // Using embedded because, it is unlikely to be using all the info for the groups
    // and the group id and name are sufficient info for most cases.
    // This socialIdea is sharing to the groups
    // < group id, group name>
    @Size(max = 10)
    private Map<String,String > groups = new HashMap<String,String >();

    // this socialIdea is sharing to the individual users
    @Size(max = 1000)
    private Map<String,String > individualUsers = new HashMap<String,String >();








    /** Getter and Setter **/
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, Map<String, String>> getIdeas() {
        return ideas;
    }

    public void setIdeas(Map<String, Map<String, String>> ideas) {
        this.ideas = ideas;
    }


    public Map<String, String> getIndividualUsers() {
        return individualUsers;
    }

    public void setIndividualUsers(Map<String, String> individualUsers) {
        this.individualUsers = individualUsers;
    }

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }
}
