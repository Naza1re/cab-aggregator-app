package com.example.driverservice.integration;

import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.exception.error.AppError;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.example.driverservice.util.ExceptionMessages.DRIVER_NOT_FOUND_EXCEPTION;
import static com.example.driverservice.util.IntegrationTestUtil.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(
        scripts = {
                "classpath:sql/driver/delete-data.sql",
                "classpath:sql/driver/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class DriverIntegrationTest extends DataBaseContainerConfiguration {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    @Autowired
    public DriverIntegrationTest(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @LocalServerPort
    private int port;

    @Test
    void findById_shouldReturnDriverResponse() {
        System.out.println(driverRepository.findAll());
        Driver driver = driverRepository.findById(DEFAULT_ID).get();
        DriverResponse response = driverMapper.fromEntityToResponse(driver);
        System.out.println(response);

        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .when()
                .get(PATH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);
        System.out.println(actual);

        assertThat(actual).isEqualTo(response);
    }

    @Test
    void findById_shouldReturnDriverNotFoundMessage_whenDriverNotExist() {
        AppError expected = AppError.builder()
                .message(String.format(DRIVER_NOT_FOUND_EXCEPTION, ID_NOT_FOUND))
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
    void findAll_shodReturnDriverListResponse() {
        List<Driver> allDrivers = driverRepository.findAll();
        List<DriverResponse> driverResponseList = allDrivers.stream()
                .map(driverMapper::fromEntityToResponse)
                .toList();

        DriverListResponse expected = new DriverListResponse(driverResponseList);

        var actual = given()
                .port(port)
                .when()
                .get(FIND_ALL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverListResponse.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void changeStatusForDriver_shouldReturnDriverResponse_whenDriverExist() {
        DriverResponse expected = getChangeStatusDriverResponse();

        var actual = given()
                .port(port)
                .pathParam(PATH_PARAM_ID, DEFAULT_ID)
                .contentType(ContentType.JSON)
                .when()
                .put(CHANGE_STATUS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverResponse.class);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void updateDriver_shouldReturnDriverResponse_whenDriverExist() {
        DriverResponse expected = getExpectedUpdateDriverResponse();
        DriverRequest updateRequest = getUpdateDriverRequest();

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
                .as(DriverResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

}
