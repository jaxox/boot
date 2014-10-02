package com.boot.service;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 4/8/14
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.dto.LoginDTO;
import com.boot.dto.SignupForm;
import com.boot.model.SocialIdea;
import com.boot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AccountService {

    /**
     * Gets User object by user login userId.
     *
     * @param userId
     * @return null if not found
     */
    User findByUserId(String userId);

    User findByLogin(String login);

    /**
     * Gets all users in the database.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @return
     */
    List<User> getAllUsers();

    /**
     * Gets all users in the database page by page.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @param pageable
     * @return
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Gets all social connection records for specific user.
     *
     * @param userId
     * @return
     */
   // List<UserSocialConnection> getConnectionsByUserId(String userId);

    /**
     * Creates a new User with user social network account Connection Data
     *
     * @param data
     * @return
     */
   // User createUser(ConnectionData data);

    /**
     * Updates user's profile data, like email, display name, etc.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @param userId
     * @param displayName
     * @param email
     * @param webSite
     * @param imageUrl
     * @return
     * @throws UsernameNotFoundException
     *
     */
//    User updateProfile(String userId, String displayName, String email, String webSite, String imageUrl)
//            throws UsernameNotFoundException;

    /**
     * Updates user's profile data, like email, display name, etc.
     * SECURITY: Current logged in user must have ROLE_USER.
     *
     * @param userId
     * @param displayName
     * @param email
     * @param webSite
     * @return
     * @throws UsernameNotFoundException
     */
//    User updateProfile(String userId, String displayName, String email, String webSite)
//            throws UsernameNotFoundException;

    /**
     * Updates user's profile photo image URL.
     * SECURITY: Current logged in user must have ROLE_USER.
     *
     * @param userId
     * @param imageUrl
     * @return
     */
    User updateImageUrl(String userId, String imageUrl);

    /**
     * Set user account to locked or unlocked.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @param userId
     * @param locked
     */
    void lockAccount(String userId, boolean locked);

    /**
     * Set user account to trusted or untrusted.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @param userId
     * @param trusted
     */
    void trustAccount(String userId, boolean trusted);

    /**
     * Add Author role to user account or remove Author role from user account.
     * SECURITY: Current logged in user must have ROLE_ADMIN.
     *
     * @param userId
     * @param isAuthor
     */
    void setAuthor(String userId, boolean isAuthor);

    User createUserAccount(SignupForm signupForm, WebRequest request);

    boolean activateAccount(String token);

    void resendAccountActivationEmail(String email);

    User login(LoginDTO request);



    /**
     * Gets all SocialIdeas that is shared to the user.
     *
     * @return
     */
    List<SocialIdea> getAllSocialIdeasSharedTo(String userId);


    /**
     * Gets all SocialIdeas that is created by the user.
     *
     * @return
     */
    List<SocialIdea> getAllSocialIdeas(String userId);

    SignupForm getSignupForm(WebRequest request);

}