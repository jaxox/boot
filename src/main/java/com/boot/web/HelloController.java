package com.boot.web;

import com.boot.model.User;
import com.boot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/users", method= RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return accountService.createUserAccount(user);
    }

//
//    @RequestMapping(value="/users", method= RequestMethod.POST)
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        return new ResponseEntity<>(accountService.createUserAccount(user), HttpHeaderUtils.getHeader4Json(),HttpStatus.CREATED);
//    }


}