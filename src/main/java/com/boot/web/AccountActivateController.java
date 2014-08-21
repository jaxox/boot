package com.boot.web;


import com.boot.dto.ResendActivation;
import com.boot.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 10/21/13
 * Time: 1:44 PM
 */
@RestController
public class AccountActivateController {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/activation",method = RequestMethod.GET)
    public ModelAndView activateAccount(HttpServletRequest request) {
        boolean isActivationSucceed = accountService.activateAccount(request.getParameter("token"));
        ModelAndView modelAndView = new ModelAndView("accountActivation");
        modelAndView.addObject("isActivationSucceed", isActivationSucceed);
        return modelAndView;
    }



    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/api/resendactivation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmailWithToken(@RequestBody @Valid ResendActivation request) {
        accountService.resendAccountActivationEmail(request.getPrimaryEmail());
    }


}