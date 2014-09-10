package com.boot.security;

import com.boot.exception.UnauthorizedRequestException;
import com.boot.model.User;
import com.boot.repository.UserRepository;
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
    private UserRepository userRep;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        boolean isSuperClass = UsernamePasswordAuthenticationToken.class.equals(authentication.getClass());

        String email;
        String plainPassword;

        if (isSuperClass) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            email = token.getName();
            plainPassword = (String)token.getCredentials();
        }else{
            RestToken token = (RestToken) authentication;
            email = token.getKey();
            plainPassword = token.getCredentials();
        }

        User user = userRep.findByPrimaryEmail(StringUtils.normalizeEmail(email));

        if ( user==null || !passwordEncoder.matches(plainPassword,user.getEncryptPassword()) ) {
            logger.debug("isSuperClass:" + isSuperClass+
                         " Email before normalized:" +email+
                         " After normalized:"+StringUtils.normalizeEmail(email)+
                         " isUserFound:"+ (user!=null) +
                         " isPasswordMatch:" + ((user!=null)? passwordEncoder.matches(plainPassword,user.getEncryptPassword()) : "false") );
            throw new UnauthorizedRequestException("username and/or password do not match");
        }

        //TODO: is admin
        //user.getRoleType();
        //return getAuthenticatedUser(key, credentials, isAdmin);

        return getAuthenticatedUser(email, plainPassword, isSuperClass);
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
    public boolean supports(Class<?> authentication) {
        boolean isSuperClasss = UsernamePasswordAuthenticationToken.class.equals(authentication);
        return isSuperClasss || RestToken.class.equals(authentication);
    }


}