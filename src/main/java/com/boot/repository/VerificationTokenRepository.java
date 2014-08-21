package com.boot.repository;

/**
 * Created with IntelliJ IDEA.
 * User: jyu
 * Date: 8/4/14
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */

import com.boot.enums.TokenType;
import com.boot.model.User;
import com.boot.model.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken,BigInteger> {

//    User findById(String id);
//
      VerificationToken findByToken(String token);

      VerificationToken findByTokenTypeAndUserAndVerifiedIsFalse(TokenType type,User user);
//
//    Page findAll(Pageable pageable);

}
