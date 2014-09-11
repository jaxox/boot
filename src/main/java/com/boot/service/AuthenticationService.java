package com.boot.service;

import com.boot.exception.UnauthorizedRequestException;
import com.boot.model.User;
import com.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * User: jyu
 * Date: 9/11/14
 */
@Service("authenticationService")
public class AuthenticationService {

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

}
