package com.example.ratingservice.integration;

import com.example.ratingservice.dto.request.CreateRequest;
import com.example.ratingservice.dto.request.UpdateRequest;
import com.example.ratingservice.dto.responce.DriverRatingResponse;
import com.example.ratingservice.exception.appError.AppError;
import com.example.ratingservice.mapper.DriverMapper;
import com.example.ratingservice.model.DriverRating;
import com.example.ratingservice.repository.DriverRatingRepository;
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
                "classpath:sql/driver-rating/delete-data.sql",
                "classpath:sql/driver-rating/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)

public class DriverRatingIntegrationTest extends DataBaseContainerConfiguration {

    private final DriverRatingRepository driverRatingRepository;
    private final DriverMapper driverMapper;
    @Autowired
    public DriverRatingIntegrationTest(DriverRatingRepository driverRatingRepository,DriverMapper driverMapper) {
        this.driverMapper = driverMapper;
        this.driverRatingRepository=driverRatingRepository;
    }

    @LocalServerPort
    private int port;

    @Test
    void getDriverRating_shouldReturnDriverRatingResponse_whenDriveRatingExist() {
        DriverRating driverRating = driverRatingRepository.findById(DEFAULT_ID).get();
        System.out.println(driverRating);
        DriverRatingResponse expected = driverMapper.fromEntityToResponse(driverRating);

        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .when()
                .get(PATH_ID_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getDriverRating_shouldReturnDriverRatingNotFound_whenDriveRatingIsNotExist() {
        AppError expected = AppError.builder()
                .message(String.format(ExceptionMessages.DRIVER_RATING_NOT_FOUND, ID_NOT_FOUND))
                .build();


        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, ID_NOT_FOUND)
                .when()
                .get(PATH_ID_DRIVER)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(AppError.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createDriverRating_shouldReturnDriverRatingResponse_whenDriverRatingNotExist() {
        CreateRequest request = getCreateRequest();
        DriverRatingResponse expected = getCreateDriverRatingResponse();

        var actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(PATH_DEFAULT_DRIVER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DriverRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void deleteDriverRating_shouldReturnNoContent_whenDriverExist() {
                given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .when()
                .delete(PATH_ID_DRIVER)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @Test
    void updateDriverRating_shouldReturnDriverRatingResponse_whenDriverRatingExist() {
        DriverRatingResponse expected = genUpdateDriverRatingResponse();
        UpdateRequest updateRequest = getUpdateRequest();

        var actual = given()
                .port(port)
                .body(updateRequest)
                .contentType(ContentType.JSON)
                .when()
                .put(PATH_DEFAULT_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverRatingResponse.class);

        assertThat(actual).isEqualTo(expected);
    }
}
