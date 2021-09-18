package edu.fra.uas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.fra.uas.model.PayUser;
import edu.fra.uas.model.State;
import edu.fra.uas.repository.PayUserRepository;

import javax.annotation.PostConstruct;

@Component
public class InitializeDB {

    private static final Logger log = LoggerFactory.getLogger(InitializeDB.class);

    @Autowired 
    private PayUserRepository payUserRepository;

    @PostConstruct
    public void init()  {

            log.debug(">>> Db initialized");

            PayUser payUser = new PayUser();
            payUser.setName("admin");
            payUser.setState(State.suspended);
            payUserRepository.save(payUser);

            payUser = new PayUser();
            payUser.setName("bob");
            payUser.setState(State.available);
            payUserRepository.save(payUser);

            payUser = new PayUser();
            payUser.setName("alice");
            payUser.setState(State.available);
            payUserRepository.save(payUser);

    }
}
