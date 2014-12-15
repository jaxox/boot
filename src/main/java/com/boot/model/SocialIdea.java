package com.boot.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * User: jyu on 9/4/14
 *
 */
public class SocialIdea extends AbstractDocument {

    @NotNull
    @NumberFormat
    private String creatorId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**Embedded documents**/
    @Size(max = 10)
    private List<SocialIdeaItem> ideas = new LinkedList<SocialIdeaItem>();

    @Size(max = 10)
    private List<SocialIdeaItem> locations = new LinkedList<SocialIdeaItem>();

    // Using embedded because, it is unlikely to be using all the info for the groups
    // and the group id and name are sufficient info for most cases.
    // This socialIdea is sharing to the groups
    // < group id, group name>
    @Size(max = 10)
    private Map<String,String > groups = new HashMap<String,String >();

    // this socialIdea is sharing to the individual users
//    @Size(max = 1000)
//    private Map<String,String > individualUsers = new HashMap<String,String >();

    @Size(max = 100)
    private List<IndividualUserNode> individualUsers = new LinkedList<IndividualUserNode>();








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

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public List<IndividualUserNode> getIndividualUsers() {
        return individualUsers;
    }

    public void setIndividualUsers(List<IndividualUserNode> individualUsers) {
        this.individualUsers = individualUsers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<SocialIdeaItem> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<SocialIdeaItem> ideas) {
        this.ideas = ideas;
    }

    public List<SocialIdeaItem> getLocations() {
        return locations;
    }

    public void setLocations(List<SocialIdeaItem> locations) {
        this.locations = locations;
    }
}
