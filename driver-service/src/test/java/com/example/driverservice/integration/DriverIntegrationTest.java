package com.example.driverservice.integration;

import com.example.driverservice.exception.error.AppError;
import com.example.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static com.example.driverservice.util.ExceptionMessages.DRIVER_NOT_FOUND_EXCEPTION;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DriverIntegrationTest extends ContainerConfiguration {

    private final DriverRepository driverRepository;

    @LocalServerPort
    private int port;

    @Test
    void findById_shouldReturnNotFoundResponse_whenPassengerNotExist() {
        AppError expected = AppError.builder()
                .message(String.format(DRIVER_NOT_FOUND_EXCEPTION, 100L))
                .build();

        var actual = given()
                .port(port)
                .pathParam("id", 100L)
                .when()
                .get("api/v1/drivers/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .as(AppError.class);

        assertThat(actual).isEqualTo(expected);
    }


}
