package com.boot.service;

import com.boot.exception.BadRequestException;
import com.boot.exception.ObjectNotFoundException;
import com.boot.model.IndividualUserNode;
import com.boot.model.SocialIdea;
import com.boot.model.User;
import com.boot.repository.SocialIdeaRepository;
import com.boot.repository.UserRepository;
import com.boot.util.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    final static Logger logger = LoggerFactory.getLogger(SocialIdeaServiceImpl.class);

    @Autowired
    SocialIdeaRepository socialIdeaRep;
    @Autowired
    UserRepository userRep;

    @Autowired
    AuthorizationService authorizationService;

    @Override
    public SocialIdea create(SocialIdea socialIdea) {
        //Make sure the request is authorized
        authorizationService.checkAuthorization(socialIdea.getCreatorId());

        //Ideas can't contain any 'likes' info when first created
        for(Map<String,String> likes : socialIdea.getIdeas().values()){
            if( likes.size() > 0 ){
                throw new BadRequestException("Ideas can't contain any 'likes' info when first created");
            }
        }
        return socialIdeaRep.save(socialIdea);
    }

    /**
     * SECURITY: has to be the owner
     *
     * Once the socialIdea is created, the ideas can't be modified
     *
     * @param id
     * @param socialIdea
     * @return
     */
    @Override
    public SocialIdea update(String id, SocialIdea socialIdea) {

        authorizationService.checkAuthorization(socialIdea.getCreatorId());

        //Todo: find a better way to do the testing to have the id in the json file instead of hardcoded
        // and should be put it back once it is on production
//        if(!id.equals(socialIdea.getId())){
//            throw new BadRequestException("Request URL's id doesn't match the Json's id");
//        }

        SocialIdea socialIdeaInDB = socialIdeaRep.findOne(id);
        RestPreconditions.checkFound(socialIdeaInDB,id.toString());

        //merge
        //why not just saving it directly?
        //maybe Bugs? I have created the bug https://jira.spring.io/browse/DATAJPA-609
        // I have the API controller that take json request and transform the json (@RequestBody) to an objectA,
        // when it contains the object id and after the object is saved using MongoRepository (save(objectA) method).
        // and the @CreatedDate attribute will be set to null.
        socialIdeaInDB.setDescription(socialIdea.getDescription());
        socialIdeaInDB.setStartTime(socialIdea.getStartTime());
        socialIdeaInDB.setEndTime(socialIdea.getEndTime());
        socialIdeaInDB.setLocation(socialIdea.getLocation());

        return socialIdeaRep.save(socialIdeaInDB);
    }

    @Override
    public void delete(String id) {
        SocialIdea socialIdea = socialIdeaRep.findById(id);
        if(socialIdea==null){
            throw new ObjectNotFoundException(id+" is not found");
        }
        authorizationService.checkAuthorization(socialIdea.getCreatorId());
        socialIdeaRep.delete(id);
    }




    /** Working on now  **/

    /**
     * SECURITY:
     *
     * Update the SocialIdea obj which contains the ideas list; when A user likes the idea/s, put the user's id and username
     * in the idea map that mapped to the idea, so we know whom like the idea. the username is just for fast look up, instead
     * of querying all the ids and find out their names.
     *
     * TODO:
     * 1) depends on the idea's security (sharing) level is being set by the owner
     *    e.g. can be friends only, specific group or selective users only
     * 2)
     * @return
     */

    public SocialIdea like(String socialIdeaId, Set<String> likedIdeaNames) {
        //Make sure the SocialIdea is the DB
        SocialIdea socialIdeaInDB = socialIdeaRep.findOne(socialIdeaId);
        RestPreconditions.checkFound(socialIdeaInDB,socialIdeaId.toString());

        //Prepare the info for saving the "like" info
        User loggedInUser = authorizationService.getLoggedInUser();
        String loggedInUserId = loggedInUser.getId();
        String userName = loggedInUser.getUserName();

        //Loop thr the user liked's idea name and update to the SocialIdea obj
        Map<String, Map<String, String>> ideas = socialIdeaInDB.getIdeas();
        for(String ideaName : likedIdeaNames){
            Map<String, String> idea = ideas.get(ideaName);
            if(idea!=null){
                idea.put(loggedInUserId,userName);
            }
        }

        return socialIdeaRep.save(socialIdeaInDB);
    }

    /**
     * TODO: Add new users, and those users should be already friend with the socialIdea's owner/creator.
     *
     * @param socialIdeaId
     * @param newUsers
     * @return
     */
    @Override
    public SocialIdea addUsers(String socialIdeaId, Map<String,String> newUsers) {

        //Make sure the SocialIdea is the DB
        SocialIdea socialIdeaInDB = socialIdeaRep.findOne(socialIdeaId);
        RestPreconditions.checkFound(socialIdeaInDB,socialIdeaId);
        //Is owner?
        authorizationService.checkAuthorization(socialIdeaInDB.getCreatorId());
        //Currently persisted map and use it to add the new ones
        List<IndividualUserNode> persistedUsers = socialIdeaInDB.getIndividualUsers();

        //Find the users that are in persistance
        Set<String> newUserIds = newUsers.keySet();
        List<User> foundedUsers = userRep.findByIdIn(newUserIds);

        //For debugs TODO: should it throws an error if one or more IDs isn't in the DB?
        if(newUserIds.size()!=foundedUsers.size()){
            StringBuilder notFound = new StringBuilder();
            for(User newUser: foundedUsers){
                boolean found = newUserIds.contains(newUser.getId());
                if(!found){
                    notFound.append(newUser.getId()).append(",");
                }
            }
            logger.info("Adding "+newUserIds.size()+" user/s but only found "+foundedUsers.size() + notFound.toString());
        }


        for(User newUser : foundedUsers){
            persistedUsers.add(new IndividualUserNode(newUser.getId(), newUser.getUserName() ) );
        }

        return socialIdeaRep.save(socialIdeaInDB);
    }

    @Override
    public SocialIdea addGroups(String socialIdeaId, Map<String,String> SharedToGroups) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }






    /** End **/

    @Override
    public void delete(SocialIdea socialIdea) {
        authorizationService.checkAuthorization(socialIdea.getCreatorId());
        socialIdeaRep.delete(socialIdea);
    }





    @Override
    public SocialIdea findById(String id) {
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
