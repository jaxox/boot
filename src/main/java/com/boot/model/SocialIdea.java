package com.boot.model;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/4/14
 * Time: 2:50 PM
 */
public class SocialIdea extends AbstractDocument {

    @NotNull
    @NumberFormat
    private BigInteger creatorId;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;

    /**Embedded documents**/
    //Map< [NameOfIdeas] , Map< [UserId who likes the idea] , [UserName] >>
    @Size(min = 1, max = 10)
    private Map<String,Map<BigInteger,String >> ideas = new LinkedHashMap<String,Map<BigInteger,String >>();





    /** Getter and Setter **/
    public BigInteger getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(BigInteger creatorId) {
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

    public Map<String, Map<BigInteger, String>> getIdeas() {
        return ideas;
    }

    public void setIdeas(Map<String, Map<BigInteger, String>> ideas) {
        this.ideas = ideas;
    }



}
