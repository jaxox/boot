package com.boot.repository;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<User,String> {

    User findById(String id);

    List<User> findByIdIn(Set<String> ids);

    User findByPrimaryEmail(String email);

    Page findAll(Pageable pageable);

}
