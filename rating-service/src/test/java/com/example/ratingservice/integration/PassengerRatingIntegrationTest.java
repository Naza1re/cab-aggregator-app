package com.example.ratingservice.integration;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.PassengerRatingResponse;
import com.example.ratingservice.exception.appError.AppError;
import com.example.ratingservice.mapper.PassengerMapper;
import com.example.ratingservice.model.PassengerRating;
import com.example.ratingservice.repository.PassengerRatingRepository;
import com.example.ratingservice.util.ExceptionMessages;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static com.example.ratingservice.util.IntegrationTestUtil.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    void getPassengerRating_shouldReturnPassengerRatingResponse_whenDriveRatingExist() {
        PassengerRating passengerRating = passengerRatingRepository.findById(DEFAULT_ID).get();
        PassengerRatingResponse expected = passengerMapper.fromEntityToResponse(passengerRating);

        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .when()
                .get(PATH_ID_PASSENGER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPassengerRating_shouldReturnPassengerRatingNotFound_whenPassengerRatingIsNotExist() {
        AppError expected = AppError.builder()
                .message(String.format(ExceptionMessages.PASSENGER_RATING_NOT_FOUND, ID_NOT_FOUND))
                .build();


        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, ID_NOT_FOUND)
                .when()
                .get(PATH_ID_PASSENGER)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(AppError.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createPassengerRating_shouldReturnPassengerRatingResponse_whenPassengerRatingNotExist() {
        CreateRequest request = getCreateRequest();
        PassengerRatingResponse expected = getCreatePassengerRatingResponse();

        var actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(PATH_DEFAULT_PASSENGER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(PassengerRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deletePassengerRating_shouldReturnNoContent_whenPassengerExist() {
        given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .when()
                .delete(PATH_ID_PASSENGER)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @Test
    void updatePassengerRating_shouldReturnPassengerRatingResponse_whenPassengerRatingExist() {
        PassengerRatingResponse expected = genUpdatePassengerRatingResponse();
        UpdateRequest updateRequest = getUpdateRequest();

        var actual = given()
                .port(port)
                .body(updateRequest)
                .contentType(ContentType.JSON)
                .when()
                .put(PATH_DEFAULT_PASSENGER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PassengerRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }


}
