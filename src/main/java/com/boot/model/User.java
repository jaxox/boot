package com.boot.model;

import com.boot.enums.AccountStatus;
import com.boot.enums.UserRole;
import com.boot.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Enumerated;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:01 PM
  */
public class User extends AbstractDocument{

    @Email
    @NotEmpty
    @Length(min = 3 , max = 50)
    private String primaryEmail;
    @NotEmpty
    @Transient
    private String password;
    private String encryptPassword;
    private String userName;


    /**Enums**/
    @Enumerated
    private UserRole userRole = UserRole.USER; // Default value
    @Enumerated
    private AccountStatus accountStatus = AccountStatus.INACTIVE; // Default value


    /**Embedded documents**/
    private Map<String,ContactNode> contactInfo = new LinkedHashMap<String,ContactNode>();


    /**Reference**/
    //private UserProfile userProfile;



    @JsonIgnore
    public boolean isActive(){
        return accountStatus == AccountStatus.ACTIVE;
    }



    /** Getter and Setter **/
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

    public Map<String, ContactNode> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, ContactNode> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
