package com.boot.web;


import com.boot.model.User;
import com.boot.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * <p/>
 * Date: 10/21/13
 * Time: 1:44 PM
 */
@RequestMapping("/api/login")
@Controller
public class LoginController  {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private AccountService accountService;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User login(@RequestBody @Valid User request) {
        return accountService.login(request);
    }


}
