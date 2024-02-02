package com.example.ratingservice.integration;

import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.repository.PassengerRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@Sql(
        scripts = {
                "classpath:sql/passenger-rating/delete-data.sql",
                "classpath:sql/passenger-rating/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PassengerRatingIntegrationTest extends DataBaseContainerConfiguration {

    @LocalServerPort
    private int port;

    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerMapper passengerMapper;





}
