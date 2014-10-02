package com.boot.security;

import com.boot.exception.UnauthorizedRequestException;
import com.boot.model.User;
import com.boot.service.AccountService;
import com.boot.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 11/5/13
 * Time: 4:57 PM
 */
@Service
public class RestAuthenticationProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        boolean isSuperClass = UsernamePasswordAuthenticationToken.class.equals(authentication.getClass());

        String login;//can be username or email
        String plainPassword;

        if (isSuperClass) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            login = token.getName();
            plainPassword = (String)token.getCredentials();
        }else{
            RestToken token = (RestToken) authentication;
            login = token.getKey();
            plainPassword = token.getCredentials();
        }

        User user = accountService.findByLogin(login);


        if ( user==null || !passwordEncoder.matches(plainPassword,user.getEncryptPassword()) ) {
            logger.debug("isSuperClass:" + isSuperClass+
                         " Login before normalized:" +login+
                         " After normalized:"+StringUtils.normalizeStr(login)+
                         " isUserFound:"+ (user!=null) +
                         " isPasswordMatch:" + ((user!=null)? passwordEncoder.matches(plainPassword,user.getEncryptPassword()) : "false") );
            throw new UnauthorizedRequestException("username and/or password do not match");
        }

        //TODO: is admin
        //user.getRoleType();
        //return getAuthenticatedUser(key, credentials, isAdmin);

        return getAuthenticatedUser(login, plainPassword, isSuperClass);
    }

    private Authentication getAuthenticatedUser(String key, String credentials, boolean isSuperClass) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(isSuperClass){
            return new UsernamePasswordAuthenticationToken(key,credentials,authorities);
        }
        return new RestToken(key, credentials, authorities);
    }


    /* Determines if this class can support the token provided by the filter. */
    @Override
    public boolean supports(Class<?> clazz) {
        // return AbstractAuthenticationToken.class.isAssignableFrom(authentication.getClass());
        return UsernamePasswordAuthenticationToken.class.equals(clazz) || RestToken.class.equals(clazz);
    }


}