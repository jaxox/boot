package com.boot.web;


import com.boot.model.SocialIdea;
import com.boot.service.SocialIdeaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * <p/>
 * Date: 10/21/13
 * Time: 1:44 PM
 */
@RequestMapping("/api/secure/ideas")
@RestController
public class SocialIdeaController {

    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    private SocialIdeaService socialIdeaService;

    /**
     * Create an SocialIdea
     * @param request
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SocialIdea create(@RequestBody @Valid SocialIdea request) {
        return socialIdeaService.create(request);
    }


    /**
     * Security: Owner
     * Update the SocialIdea
     * - No auto validation; as ideas can't be changed
     * - Only can update the description, startTime, endTime and location
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SocialIdea update(@PathVariable("id") String id, @RequestBody SocialIdea request) {
        return socialIdeaService.update(id,request);
    }


    /**
     * Security: Depends on the SocialIdea's security setting
     *
     * Update the SocialIdea
     * - No auto validation; as ideas can't be changed
     * - Only can update the description, startTime, endTime and location
     *
     *
     * @param id
     *        It is the SocialIdea object id
     * @param request
     *        The list of idea names that the logged in user liked     *
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SocialIdea updateWhomLikeTheIdea(@PathVariable("id") String id, @RequestBody HashSet<String> request) {
        return socialIdeaService.like(id, request);
    }



    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}/users", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public SocialIdea updateSharedToWhom(@PathVariable("id") String id,  @RequestBody HashMap<String,String> request) {
        return socialIdeaService.addUsers(id,request);
    }


    /**
     * Delete a SocialIdea
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") String id) {
        socialIdeaService.delete(id);
    }





}
