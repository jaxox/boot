package com.boot.model;

import com.boot.enums.TokenType;
import org.joda.time.DateTime;

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
 * A token that gives the user permission to carry out a specific task once within a determined time period.
 * An example would be a Lost Password token. The user receives the token embedded in a link.
 * They send the token back to the server by clicking the link and the action is processed
 *
 * @version 1.0
 * @author: Iain Porter iain.porter@porterhead.com
 * @since 10/09/2012
 */
public class VerificationToken {



    public static final int EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours

    @Id
    private BigInteger id;
    private final String token;
    private Date expiryDate;
    private boolean verified;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public VerificationToken(TokenType tokenType) {
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


}
