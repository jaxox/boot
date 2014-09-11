package com.boot.service;

import com.boot.model.SocialIdea;
import com.boot.repository.SocialIdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 9/10/14
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("socialIdeaService")
public class SocialIdeaServiceImpl implements SocialIdeaService {

    @Autowired
    SocialIdeaRepository socialIdeaRep;

    /** Working on now  **/
    @Override
    public SocialIdea create(SocialIdea socialIdea) {
        return socialIdeaRep.save(socialIdea);
    }
    /** End **/

    @Override
    public SocialIdea update(BigInteger id, SocialIdea socialIdea) {
        return socialIdeaRep.save(socialIdea);
    }

    @Override
    public void delete(SocialIdea socialIdea) {
        socialIdeaRep.delete(socialIdea);
    }

    @Override
    public void delete(BigInteger id) {
        socialIdeaRep.delete(id);
    }

    @Override
    public SocialIdea findById(String id) {
        return findById(new BigInteger(id));
    }

    @Override
    public SocialIdea findById(BigInteger id) {
        return socialIdeaRep.findOne(id);
    }

    @Override
    public List<SocialIdea> getAll() {
        return socialIdeaRep.findAll();
    }

    @Override
    public Page<SocialIdea> getAll(Pageable pageable) {
        return socialIdeaRep.findAll(pageable);
    }




}
