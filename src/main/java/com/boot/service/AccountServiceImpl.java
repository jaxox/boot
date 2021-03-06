package com.boot.service;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 4/8/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.dto.LoginDTO;
import com.boot.dto.SignupForm;
import com.boot.enums.AccountStatus;
import com.boot.enums.TokenType;
import com.boot.exception.DuplicateEntryException;
import com.boot.exception.UnauthorizedRequestException;
import com.boot.message.Message;
import com.boot.message.MessageType;
import com.boot.model.SocialIdea;
import com.boot.model.User;
import com.boot.model.VerificationToken;
import com.boot.repository.SocialIdeaRepository;
import com.boot.repository.UserRepository;
import com.boot.repository.VerificationTokenRepository;
import com.boot.util.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Locale;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Value("${custom.server.base.url}")
    String url;

    @Autowired private SocialIdeaRepository socialIdeaRep;
    @Autowired private UserRepository userRep;
    @Autowired private VerificationTokenRepository tokenRep;
    @Autowired private EmailService emailService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private AuthorizationService authorizationService;

    private final ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();


//    @Override
//    public User createUserAccount(User user, WebRequest request) {
//
//        String email = user.getPrimaryEmail();
//
//        if( userRep.findByPrimaryEmail(email)!=null ){
//           throw new DuplicateEntryException(email + " is already registered ");
//        }
//
//        //the User Id is needed for the token
//        user = userRep.save(user);
//        VerificationToken token = tokenRep.save(new VerificationToken(user,TokenType.ACCOUNT_ACTIVATION));
//
//
//        //Preparing Email
//        final Context ctx = new Context(Locale.ENGLISH);
//        ctx.setVariable("token", token.getToken());
//        ctx.setVariable("baseUrl", url);
//
//        emailService.sendEmail(email, EmailService.EmailTemplate.ACTIVATION, ctx);
//
//        providerSignInUtils.doPostSignUp(user.getPrimaryEmail(), request);
//
//        return user;
//    }

    @Override
    public User createUserAccount(SignupForm signupForm, WebRequest request) {

        String username = StringUtils.normalizeStr(signupForm.getUsername());
        String email = StringUtils.normalizeStr(signupForm.getPrimaryEmail());

        if( userRep.findByUsername(username)!=null ){
            throw new DuplicateEntryException(  username + " is already registered ");
        }

        if( userRep.findByPrimaryEmail(email)!=null ){
            throw new DuplicateEntryException(  email + " is already registered ");
        }

        providerSignInUtils.doPostSignUp(username, request);

        return userRep.save(new User(signupForm.getPassword(),username,email));
    }

    @Override
    public boolean activateAccount(String tokenStr) {

        VerificationToken token = tokenRep.findByToken(tokenStr);
        if(token==null ){
            return false;
        }

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

        User user = userRep.findByPrimaryEmail(StringUtils.normalizeStr(email));

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
    public User login(LoginDTO request) {

        String login = StringUtils.normalizeStr(request.getLogin());

        try {
            Authentication token = new UsernamePasswordAuthenticationToken(login, request.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (AuthenticationException ex) {  //for authenticationManger.authenticate
            logger.debug(ex.toString() + " E:" + request.getLogin() + " P:" + request.getPassword());
            throw new UnauthorizedRequestException(ex.toString());
        }

        //TODO: is there a better way to authenticate and getting the user at the same time without query twice?
        return findByLogin(login);
    }

    @Override
    public List<SocialIdea> getAllSocialIdeasSharedTo(String userId) {
        authorizationService.checkAuthorization(userId);
        return socialIdeaRep.findByIndividualUsersUid(userId);
    }


    @Override
    public List<SocialIdea> getAllSocialIdeas(String userId) {
        authorizationService.checkAuthorization(userId);
        return socialIdeaRep.findByCreatorId(userId);
    }

    @Override
    public SignupForm getSignupForm(WebRequest request) {

        Connection<?> connection =  providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            request.setAttribute("message", new Message(MessageType.INFO, "Your " + org.springframework.util.StringUtils.capitalize(connection.getKey().getProviderId()) + " account is not associated with a Spring Social Showcase account. If you're new, please sign up."), WebRequest.SCOPE_REQUEST);
            return SignupForm.fromProviderUser(connection.fetchUserProfile());
        } else {
            return new SignupForm();
        }
    }



    public Connection<?> getSocailConnectionFromSession(WebRequest request) {
        return providerSignInUtils.getConnectionFromSession(request);
    }



    //Not yet done

    @Override
    public User findByUserId(String userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User findByLogin(String login) {
        User user = (EmailValidator.getInstance().isValid(login)) ? userRep.findByPrimaryEmail(login) : userRep.findByUsername(login);

        if(user==null){
            throw new UnauthorizedRequestException("User not found");
        }
        return user;
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