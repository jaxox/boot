package com.boot.security;

import com.boot.model.User;
import org.springframework.data.domain.AuditorAware;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/9/14
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    /**
       Not sure why have to have this class otherwise the @CreatedDate is not working
     */
    public User getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        //return ((MyUserDetails) authentication.getPrincipal()).getUser();
//
//        Object whatisthat = authentication.getPrincipal();
        return null;
    }
}