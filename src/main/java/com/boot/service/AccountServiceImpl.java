package com.boot.service;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 4/8/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.enums.TokenType;
import com.boot.exception.DuplicateEntryException;
import com.boot.model.User;
import com.boot.model.VerificationToken;
import com.boot.repository.UserRepository;
import com.boot.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    final static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired private UserRepository userRep;

    @Override
    public User createUserAccount(User user) {

        String normalizedEmail = StringUtils.normalizeEmail(user.getPrimaryEmail());

        // if not null (found) means it is already stored in the persistence.
        if( userRep.findByPrimaryEmail(normalizedEmail)!=null ){
           throw new DuplicateEntryException(normalizedEmail + " is already registered ");
        }

        user.setPrimaryEmail(normalizedEmail);

        VerificationToken token = new VerificationToken(TokenType.EMAIL_VERIFICATION);
        user.addVerificationToken(token);

        String activationTokenKey = token.getToken();


        //Mapping the data in the email template!
        Map<String,Object> emailTemplateParams = new HashMap<>();
        //setEmailTemplateParam(user.getPrimaryEmail(), activationKey,emailTemplateParams);
        //emailHandler.sendEmail(user.getPrimaryEmail() ,null, subject, EmailHandler.EmailTemplate.ACTIVATION, emailTemplateParams);

        return userRep.save(user);
    }


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