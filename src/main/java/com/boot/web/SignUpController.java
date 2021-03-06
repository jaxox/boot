package com.boot.web;


import com.boot.dto.SignupForm;
import com.boot.model.User;
import com.boot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

/**
 * User: jyu
 */

@RequestMapping("/signup")
@RestController
public class SignUpController{

    @Autowired
    AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method= RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUserAccount(@RequestBody @Valid SignupForm signupForm, WebRequest request) {
        return accountService.createUserAccount(signupForm,request);
    }

    @RequestMapping(method=RequestMethod.GET)
    public SignupForm signupForm(WebRequest request) {
        return accountService.getSignupForm(request);
    }




}
