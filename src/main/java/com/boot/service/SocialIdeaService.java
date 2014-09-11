package com.boot.service;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 4/8/14
 * Time: 3:04 PM
 */

import com.boot.model.SocialIdea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

public interface SocialIdeaService {

    /**
     * Gets SocialIdea object by the id.
     *
     * @param id
     * @return null if not found
     */
    SocialIdea findById(String id);
    SocialIdea findById(BigInteger id);

    /**
     * Gets all users in the database.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @return
     */
    List<SocialIdea> getAll();

    /**
     * Gets all users in the database page by page.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     *
     *
     *
     * @param pageable
     * @return
     */
    Page getAll(Pageable pageable);



    /**
     * Creates a new SocialIdea
     *
     * @param socialIdea
     * @return
     */
    SocialIdea create(SocialIdea socialIdea);


    /**
     * Updates a SocialIdea
     *
     * @param socialIdea
     * @return
     */
    SocialIdea update(BigInteger id, SocialIdea socialIdea);

    /**
     * Deletes a SocialIdea
     *
     * @param socialIdea
     * @return
     */
    void delete(SocialIdea socialIdea);
    void delete(BigInteger id);




}