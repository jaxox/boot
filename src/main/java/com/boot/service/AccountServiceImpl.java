package com.boot.service;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 4/8/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.enums.AccountStatus;
import com.boot.enums.TokenType;
import com.boot.exception.DuplicateEntryException;
import com.boot.exception.UnauthorizedRequestException;
import com.boot.model.User;
import com.boot.model.VerificationToken;
import com.boot.repository.UserRepository;
import com.boot.repository.VerificationTokenRepository;
import com.boot.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Locale;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Value("${custom.server.base.url}")
    String url;

    @Autowired private UserRepository userRep;
    @Autowired private VerificationTokenRepository tokenRep;
    @Autowired private EmailService emailService;
    @Autowired private AuthenticationManager authenticationManager;


    @Override
    public User createUserAccount(User user) {

        String email = user.getPrimaryEmail();

        if( userRep.findByPrimaryEmail(email)!=null ){
           throw new DuplicateEntryException(email + " is already registered ");
        }

        //the User Id is needed for the token
        user = userRep.save(user);
        VerificationToken token = tokenRep.save(new VerificationToken(user,TokenType.ACCOUNT_ACTIVATION));


        //Preparing Email
        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("token", token.getToken());
        ctx.setVariable("baseUrl", url);

        emailService.sendEmail(email, EmailService.EmailTemplate.ACTIVATION, ctx);

        return user;
    }

    @Override
    public boolean activateAccount(String tokenStr) {

        VerificationToken token = tokenRep.findByToken(tokenStr);

        //Check is the token still active and is an account activation token
        boolean isAccTokenActive = token.getTokenType().equals(TokenType.ACCOUNT_ACTIVATION) && isTokenActive(token);
        if(!isAccTokenActive){
           return false;
        }

        //Activate user account
        User user = token.getUser();
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRep.save(user);

        //After activate the user, set the token is used
        token.setVerified(true);
        tokenRep.save(token);

        return true;
    }

    private boolean isTokenActive(VerificationToken token) {
        return !token.hasExpired() && !token.isVerified();
    }




    @Override
    public void resendAccountActivationEmail(String email) {

        User user = userRep.findByPrimaryEmail(StringUtils.normalizeEmail(email));

        if (user == null) {
            throw new UsernameNotFoundException("Your email is not registered: " + email);
        }

        //Reuse the token if possible; should be only one active ACCOUNT_ACTIVATION token per user
        VerificationToken token = tokenRep.findByTokenTypeAndUserAndVerifiedIsFalse(TokenType.ACCOUNT_ACTIVATION,user);
        if (token == null) {
            token = new VerificationToken(user, TokenType.ACCOUNT_ACTIVATION);
        }else{
            token.resetExpiredTime();
        }
        tokenRep.save(token);


        //Preparing Email
        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("token", token.getToken());
        ctx.setVariable("baseUrl", url);

        emailService.sendEmail(email, EmailService.EmailTemplate.ACTIVATION, ctx);
    }


    //Currently working on

    //TODO:Next - security
    @Override
    public User login(User request) {
        try {

            Authentication token = new UsernamePasswordAuthenticationToken(request.getPrimaryEmail(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRep.findByPrimaryEmail(request.getPrimaryEmail());

            if(user==null || !user.isActive()){
                throw new UnauthorizedRequestException("Inactive account");
            }

            return user;

        } catch (AuthenticationException ex) {  //for authenticationManger.authenticate
            logger.debug(ex.toString() + " E:" + request.getPrimaryEmail() + " P:" + request.getPassword());
            throw new UnauthorizedRequestException(ex.toString());
        }
    }

    //Not yet done

    @Override
    public User findByUserId(String userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<User> getAllUsers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User updateImageUrl(String userId, String imageUrl) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void lockAccount(String userId, boolean locked) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void trustAccount(String userId, boolean trusted) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAuthor(String userId, boolean isAuthor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}