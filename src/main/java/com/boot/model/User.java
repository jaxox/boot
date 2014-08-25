package com.boot.model;

import com.boot.enums.AccountStatus;
import com.boot.enums.UserRole;
import com.boot.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Document
public class User implements Serializable {

    @Id
    private BigInteger id;

    @Email
    @NotEmpty
    @Length(min = 3 , max = 50)
    private String primaryEmail;

    @NotEmpty
    @Transient
    private String password;

    private String encryptPassword;


    //Enums
    @Enumerated
    private UserRole userRole = UserRole.USER; // Default value
    @Enumerated
    private AccountStatus accountStatus = AccountStatus.INACTIVE; // Default value

    //Embedded documents


    //Reference
    //private UserProfile userProfile;

    @JsonIgnore
    public boolean isActive(){
        return accountStatus == AccountStatus.ACTIVE;
    }


    /** Getter and Setter **/
    public BigInteger getId() {

        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        //ensure the consistency of the email format
        this.primaryEmail = StringUtils.normalizeEmail(primaryEmail);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
        setEncryptPassword(password);
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }


    @JsonIgnore
    public String getEncryptPassword() {
        return encryptPassword;
    }

    @JsonProperty
    public void setEncryptPassword(String password) {
        this.encryptPassword =  new BCryptPasswordEncoder().encode(password);
    }
}
