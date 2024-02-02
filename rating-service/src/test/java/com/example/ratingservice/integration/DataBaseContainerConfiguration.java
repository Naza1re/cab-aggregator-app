package com.example.ratingservice.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataBaseContainerConfiguration {

    private static MySQLContainer<?> mySQL = new MySQLContainer<>(DockerImageName.parse(
            "mysql:8"
    ));

    @BeforeAll
    static void beforeAll() {
        mySQL.start();
    }

    @AfterAll
    static void afterAll() {
        mySQL.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQL::getJdbcUrl);
        registry.add("spring.datasource.username", mySQL::getUsername);
        registry.add("spring.datasource.password", mySQL::getPassword);
        registry.add("mysql.driver", mySQL::getDriverClassName);
    }


}
