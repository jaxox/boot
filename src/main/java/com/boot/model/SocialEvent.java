package com.boot.model;

import org.joda.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/4/14
 * Time: 2:50 PM

 */
@Document
public class SocialEvent {

    @Id
    private BigInteger id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;






}
