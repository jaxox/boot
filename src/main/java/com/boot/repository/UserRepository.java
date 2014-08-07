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

import java.math.BigInteger;

public interface UserRepository extends MongoRepository<User,BigInteger> {

    User findById(String id);

    User findByPrimaryEmail(String email);

    Page findAll(Pageable pageable);

}
