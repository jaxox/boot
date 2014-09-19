package com.boot.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:01 PM
  */
@Document
public abstract class AbstractDocument implements Serializable {

    @Id
    private String id;
    @CreatedDate
    private DateTime creationDate;
    @LastModifiedDate
    private DateTime modificationDate;



    /** Getter and Setter **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationDateStr() {
        return creationDate.toString();
    }

    public String getModificationDateStr() {
        return modificationDate.toString();
    }
}
