package com.boot.service;

import com.boot.exception.UnauthorizedRequestException;
import com.boot.model.User;
import com.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * User: jyu
 * Date: 9/11/14
 */
@Service("authorizationService")
public class AuthorizationService {

    @Autowired
    private UserRepository userRep;

    public User getLoggedInUser(){
        //Currently using the email address to login; if later on using both user name and email, need to change the logic
        String loginStr = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRep.findByPrimaryEmail(loginStr);

        if(user==null){
            throw new UnauthorizedRequestException("Unauthorized User");
        }

        return user;
    }


    public boolean checkAuthorization(BigInteger userId){
        if(!getLoggedInUser().getId().equals(userId)){
            throw new UnauthorizedRequestException("Your userId from the request is not matching with your login id");
        }
        return true;
    }

}
