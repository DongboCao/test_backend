package com.test.candidate.service;

import com.test.candidate.persistence.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by oleg on 07/08/15.
 */
@SpringBootApplication
@Import({PersistenceConfiguration.class})
public class CandidateServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CandidateServiceApp.class, args);
    }
}
