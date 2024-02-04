package com.example.rideservice.integration;

import com.example.rideservice.dto.request.RideRequest;
import com.example.rideservice.dto.response.RideListResponse;
import com.example.rideservice.dto.response.RideResponse;
import com.example.rideservice.mapper.RideMapper;
import com.example.rideservice.model.Ride;
import com.example.rideservice.repository.RideRepository;
import com.example.rideservice.util.IntegrationTestUtil;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static com.example.rideservice.util.IntegrationTestUtil.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Sql(
        scripts = {
                "classpath:sql/ride/delete-data.sql",
                "classpath:sql/ride/insert-data.sql"
        }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RideIntegrationTest extends DataBaseContainerConfiguration {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @LocalServerPort
    private int port;

    @Test
    void findAll_shouldReturnAllRides() {
        Page<Ride> ridePage = rideRepository.findAll(
                PageRequest.of(VALID_OFFSET - 1, VALID_LIMIT, Sort.by(VALID_TYPE))
        );

        List<RideResponse> expected = ridePage.stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();

        var actual = given()
                .port(port)
                .when()
                .params(Map.of(
                        PAGE_PARAM_NAME, VALID_OFFSET,
                        SIZE_PARAM_NAME, VALID_LIMIT,
                        ORDER_BY_PARAM_NAME, VALID_TYPE)
                )
                .get(IntegrationTestUtil.PATH_PAGE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList("rideList", RideResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findRideById_shouldReturnRideResponse_whenRideExist() {
        Ride ride = rideRepository.findById(DEFAULT_ID).get();
        RideResponse expected = rideMapper.fromEntityToResponse(ride);

        var actual = given()
                .port(port)
                .pathParam(ID, DEFAULT_ID)
                .when()
                .get(PATH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void createRide_shouldReturnRideResponse_whenRideValid() {
        RideResponse expected = getDefaultRideResponse();
        RideRequest rideRequest = getDefaultRideRequest();

        var actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .when()
                .post(PATH_DEFAULT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RideResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void startRide_shouldReturnRideResponse_whenRideExist() {

        var actual = given()
                .port(port)
                .pathParam(ID,DEFAULT_ID)
                .when()
                .put(PATH_START)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(actual.getStartDate()).isNotNull();
    }

    @Test
    void getAllRidesByPassengerId_shouldReturnRideListResponse() {
        List<Ride> rideList = rideRepository.getAllByPassengerId(DEFAULT_ID);
        List<RideResponse> rideResponses = rideList.stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();

        RideListResponse expected = new RideListResponse(rideResponses);


        var actual = given()
                .port(port)
                .pathParam(ID,DEFAULT_ID)
                .when()
                .get(PATH_GET_BY_PASSENGER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideListResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAllRidesByDriverId_shouldReturnRideListResponse() {
        List<Ride> rideList = rideRepository.getAllByDriverId(DEFAULT_ID);
        List<RideResponse> rideResponses = rideList.stream()
                .map(rideMapper::fromEntityToResponse)
                .toList();

        RideListResponse expected = new RideListResponse(rideResponses);

        var actual = given()
                .port(port)
                .pathParam(ID,DEFAULT_ID)
                .when()
                .get(PATH_GET_BY_DRIVER)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideListResponse.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void endRide_shouldReturnRideResponse_whenRideExist() {
        var actual = given()
                .port(port)
                .pathParam(ID,DEFAULT_ID)
                .when()
                .put(PATH_END)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideResponse.class);

        assertThat(actual.getEndDate()).isNotNull();
    }


}
