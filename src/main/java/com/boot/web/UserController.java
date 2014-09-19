package com.boot.web;


import com.boot.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * User: jyu on 10/21/14
 */
@RequestMapping("/api/secure/users")
@RestController
public class UserController {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private AccountService accountService;




    /**
     * Get a list of SocialIdeas that belong to / shared to the user
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/socialIdeas", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void listAllRelevant(@PathVariable("id") String id) {
        accountService.getAllSocialIdea(id);
    }



}
