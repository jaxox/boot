package com.boot.web;


import com.boot.model.SocialIdea;
import com.boot.service.AccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
     * Get a list of SocialIdeas that shared to the user
     *
     * Can't think of a better way to describe this on the URL resource. In general verb should be avoided.
     * but I am using shared to represent the ideas that is sharing to this users.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/socialIdeas/shared", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<SocialIdea> listAllRelevant(@PathVariable("id") String id) {
        return accountService.getAllSocialIdeasSharedTo(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/socialIdeas", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<SocialIdea> listAll(@PathVariable("id") String id) {
        return accountService.getAllSocialIdeas(id);
    }



}
