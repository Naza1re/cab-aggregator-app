package com.example.passengerservice.integration;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.exception.appError.AppError;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.util.ExceptionMessages;
import com.example.passengerservice.util.IntegrationTestUtil;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.example.passengerservice.util.IntegrationTestUtil.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(
        scripts = {
                "classpath:sql/passenger/delete-data.sql",
                "classpath:sql/passenger/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class PassengerIntegrationTest extends DataBaseContainerConfiguration {

    private final PassengerRepository  passengerRepository;
    private final PassengerMapper passengerMapper;
    @Autowired
    public PassengerIntegrationTest(PassengerMapper passengerMapper, PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @LocalServerPort
    private int port;

    @Test
    void findById_shouldReturnPassengerNotFound_whenPassengerNotExist() {
        AppError expected = AppError.builder()
                .message(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION, ID_NOT_FOUND))
                .build();

        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, ID_NOT_FOUND)
                .when()
                .get(PATH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(AppError.class);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void findAll_shodReturnPassengersListResponse() {
        List<Passenger> allPassengers = passengerRepository.findAll();
        List<PassengerResponse> passengerResponseList = allPassengers.stream()
                .map(passengerMapper::fromEntityToResponse)
                .toList();

        PassengerListResponse expected = new PassengerListResponse(passengerResponseList);

        var actual = given()
                .port(port)
                .when()
                .get(PATH_DEFAULT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerListResponse.class);
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void updateDriver_shouldReturnDriverResponse_whenPassengerExist() {
        PassengerResponse expected = getExpectedUpdatePassengerResponse();
        PassengerRequest updateRequest = getUpdatePassengerRequest();

        var actual = given()
                .port(port)
                .body(updateRequest)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .contentType(ContentType.JSON)
                .when()
                .put(PATH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deletePassenger_shouldReturnNoContent_whenDriverExist() {

        given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .contentType(ContentType.JSON)
                .when()
                .delete(PATH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }
}
