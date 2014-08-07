package com.boot.model;

import com.boot.enums.AccountStatus;
import com.boot.enums.UserRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    private String password;
    private String activationKey;

    //Enums
    @Enumerated
    private UserRole userRole = UserRole.USER; // Default value
    @Enumerated
    private AccountStatus accountStatus = AccountStatus.INACTIVE; // Default value

    //Embedded documents
    private List<VerificationToken> verificationTokens = new ArrayList<>();

    //Reference
    //private UserProfile userProfile;


    public synchronized void addVerificationToken(VerificationToken token) {
        verificationTokens.add(token);
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
        this.primaryEmail = primaryEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
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

    public List<VerificationToken> getVerificationTokens() {
        return verificationTokens;
    }

    public void setVerificationTokens(List<VerificationToken> verificationTokens) {
        this.verificationTokens = verificationTokens;
    }





    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + primaryEmail + ", age=" + password + "]";
    }

}
