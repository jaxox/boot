package com.boot.service;

import com.boot.exception.BadRequestException;
import com.boot.exception.ObjectNotFoundException;
import com.boot.model.SocialIdea;
import com.boot.model.User;
import com.boot.repository.SocialIdeaRepository;
import com.boot.util.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    AuthorizationService authorizationService;

    @Override
    public SocialIdea create(SocialIdea socialIdea) {
        //Make sure the request is authorized
        authorizationService.checkAuthorization(socialIdea.getCreatorId());

        //Ideas can't contain any 'likes' info when first created
        for(Map<BigInteger,String> likes : socialIdea.getIdeas().values()){
            if( likes.size() > 0 ){
                throw new BadRequestException("Ideas can't contain any 'likes' info when first created");
            }
        }
        return socialIdeaRep.save(socialIdea);
    }

    /**
     * SECURITY: has to be the owner
     *
     * @param id
     * @param socialIdea
     * @return
     */
    @Override
    public SocialIdea update(BigInteger id, SocialIdea socialIdea) {

        authorizationService.checkAuthorization(socialIdea.getCreatorId());

//        if(!id.equals(socialIdea.getId())){
//            throw new BadRequestException("Request URL's id doesn't match the Json's id");
//        }

        SocialIdea socialIdeaInDB = socialIdeaRep.findOne(id);
        RestPreconditions.checkFound(socialIdeaInDB,id.toString());

        //merge
        socialIdeaInDB.setDescription(socialIdea.getDescription());
        socialIdeaInDB.setStartTime(socialIdea.getStartTime());
        socialIdeaInDB.setEndTime(socialIdea.getEndTime());
        socialIdeaInDB.setLocation(socialIdea.getLocation());

        return socialIdeaRep.save(socialIdeaInDB);
    }





    /** Working on now  **/

    /**
     * SECURITY:
     *
     * Update the SocialIdea obj; when A user likes the idea/s
     *
     * @return
     */

    public SocialIdea like(BigInteger socialIdeaId, Set<String> likedIdeaNames) {
        //Make sure the SocialIdea is the DB
        SocialIdea socialIdeaInDB = socialIdeaRep.findOne(socialIdeaId);
        RestPreconditions.checkFound(socialIdeaInDB,socialIdeaId.toString());

        //Prepare the info for saving the "like" info
        User loggedInUser = authorizationService.getLoggedInUser();
        BigInteger loggedInUserId = loggedInUser.getId();
        String userName = loggedInUser.getUserName();

        //Loop thr the user liked's idea name and update tot he SocialIdea obj
        Map<String, Map<BigInteger, String>> ideas = socialIdeaInDB.getIdeas();
        for(String ideaName : likedIdeaNames){
            Map<BigInteger, String> idea = ideas.get(ideaName);
            if(idea!=null){
                idea.put(loggedInUserId,userName);
            }
        }

        return socialIdeaRep.save(socialIdeaInDB);
    }


    /** End **/

    @Override
    public void delete(SocialIdea socialIdea) {

        authorizationService.checkAuthorization(socialIdea.getCreatorId());
        socialIdeaRep.delete(socialIdea);
    }


    @Override
    public void delete(BigInteger id) {
        SocialIdea socialIdea = socialIdeaRep.findById(id.toString());
        if(socialIdea==null){
            throw new ObjectNotFoundException(id+" is not found");
        }
        authorizationService.checkAuthorization(socialIdea.getCreatorId());
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
