package com.boot.model;

import com.boot.enums.TokenType;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;


/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/12/13
 * Time: 5:28 PM
 */


/**
 * Verification Token is used for giving a permission to a specific user to perform a specific
 * task once within a pre-determined period of time without authentication.
 *
 * E.g. Verify email when register and Lost Password.
 * 1) The user receives an email with the token embedded in a link.
 * 2) User clicks on the link which sends the token back to the server and the task is processed.
 */

@Document
public class VerificationToken {

    public static final int EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours

    @Id
    private BigInteger id;

    @DBRef
    private User user;
    @Indexed
    private final String token;
    private Date expiryDate;
    private boolean verified;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public VerificationToken(User user, TokenType tokenType) {
        this.user = user;
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(EXPIRY_TIME_IN_MINS);
        this.token = UUID.randomUUID().toString();
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        DateTime now = new DateTime();
        return now.plusMinutes(expiryTimeInMinutes).toDate();
    }

    public boolean hasExpired() {
        DateTime tokenDate = new DateTime(getExpiryDate());
        return tokenDate.isBeforeNow();
    }

    public void resetExpiredTime() {
        this.expiryDate = calculateExpiryDate(EXPIRY_TIME_IN_MINS);
    }


    /** Getter and Setter **/

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
