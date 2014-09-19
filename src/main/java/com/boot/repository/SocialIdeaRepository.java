package com.boot.repository;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.model.SocialIdea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SocialIdeaRepository extends MongoRepository<SocialIdea,String> {

    SocialIdea findById(String id);

    List<SocialIdea> findByIndividualUsers(String id);

    Page findAll(Pageable pageable);


}
