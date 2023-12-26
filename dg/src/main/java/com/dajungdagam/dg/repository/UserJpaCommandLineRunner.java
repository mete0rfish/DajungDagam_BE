package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserJpaRepository repository;

//    public void createUser(String nickName, ){
//
//    }

    @Override
    public void run(String... args) throws Exception {

    }
}
