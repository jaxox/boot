package com.boot.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/19/14
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResendActivation {

    @Email
    @Basic
    @NotEmpty
    private String primaryEmail;

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }
}
