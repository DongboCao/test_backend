package com.test.candidate.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by oleg on 09/08/15.
 */
@SpringBootApplication
@Configuration
@EnableJpaRepositories
@EntityScan(basePackages = "com.test.candidate.persistence.entity")
@EnableTransactionManagement
public class PersistenceConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(PersistenceConfiguration.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2)
                .addScript("candidate-db-schema.sql")
                .addScript("candidate-test-data.sql")
                .build();
    }
}