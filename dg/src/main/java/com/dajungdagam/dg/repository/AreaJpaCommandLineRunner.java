package com.dajungdagam.dg.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

@Repository
public class AreaJpaCommandLineRunner implements CommandLineRunner {

    @Autowired
    private AreaJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {

    }
}
