package com.example.driverservice.service;

import com.example.driverservice.client.RatingFeignClient;
import com.example.driverservice.dto.request.DriverRequest;
import com.example.driverservice.dto.response.DriverListResponse;
import com.example.driverservice.dto.response.DriverPageResponse;
import com.example.driverservice.dto.response.DriverResponse;
import com.example.driverservice.exception.*;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.impl.DriverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.example.driverservice.util.DriverTestUtil.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;
    @InjectMocks
    private DriverServiceImpl driverService;
    @Mock
    private RatingFeignClient ratingFeignClient;
    @Test
    void findDriverByIdWhenDriverNotFound() {

        doReturn(Optional.empty())
                .when(driverRepository)
                .findById(NOT_FOUND_ID);

        assertThrows(
                DriverNotFoundException.class,
                () -> driverService.getDriverById(NOT_FOUND_ID)
        );

        verify(driverRepository).findById(NOT_FOUND_ID);
    }

    @Test
    void findDriverByIdWhenDriverExist() {
        Driver driver = getDefaultDriver();
        DriverResponse expectedResponse = getDefaultDriverResponse();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DEFAULT_ID);
        doReturn(expectedResponse)
                .when(driverMapper)
                .fromEntityToResponse(driver);

        DriverResponse actual = driverService.getDriverById(DEFAULT_ID);

        verify(driverRepository).findById(DEFAULT_ID);
        verify(driverMapper).fromEntityToResponse(driver);

        assertThat(actual).isEqualTo(expectedResponse);

    }

    @Test
    void findAllDrivers() {

        DriverListResponse expected = getDriverListResponse();
        System.out.println(expected);
        List<Driver> driverList = getDriverList();


        doReturn(driverList)
                .when(driverRepository)
                .findAll();
        doReturn(expected.getDriverResponseList().get(0))
                .when(driverMapper)
                .fromEntityToResponse(driverList.get(0));
        doReturn(expected.getDriverResponseList().get(1))
                .when(driverMapper)
                .fromEntityToResponse(driverList.get(1));

        DriverListResponse actual = driverService.getListOfDrivers();

        verify(driverRepository).findAll();
        verify(driverMapper).fromEntityToResponse(driverList.get(0));
        verify(driverMapper).fromEntityToResponse(driverList.get(1));

        assertThat(actual.getDriverResponseList()).isEqualTo(expected.getDriverResponseList());

    }

    @Test
    void GetPageWhenPaginationParamsIsInvalid() {
        assertThrows(
                PaginationParamException.class,
                () -> driverService.getDriverPage(INVALID_PAGINATION_PAGE,INVALID_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }

    @Test
    void GetPageWhenOrderByIsInvalid() {
        assertThrows(
                SortTypeException.class,
                () -> driverService.getDriverPage(DEFAULT_PAGINATION_PAGE,DEFAULT_PAGINATION_SIZE,INVALID_PAGINATION_SORTED_TYPE)
        );
    }

    @Test
    void changeStatusWheDriverExist() {
        Driver driver = getDefaultDriver();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DEFAULT_ID);


        driverService.changeStatus(DEFAULT_ID);

        verify(driverRepository).findById(DEFAULT_ID);
        verify(driverRepository).save(driver);

        assertFalse(driver.isAvailable());

    }

    @Test
    void createDriverWhenDataIsUnique() {
        DriverResponse response = getDefaultDriverResponse();
        DriverRequest request = getDriverRequest();
        Driver notSavedDriver = getNotSavedDriver();
        Driver savedDriver = getSavedDriver();

        doReturn(false)
                .when(driverRepository)
                .existsByEmail(DEFAULT_EMAIL);
        doReturn(false)
                .when(driverRepository)
                .existsByPhone(DEFAULT_PHONE);
        doReturn(false)
                .when(driverRepository)
                .existsByNumber(DEFAULT_NUMBER);
        doReturn(notSavedDriver)
                .when(driverMapper)
                .fromRequestToEntity(request);
        doReturn(savedDriver)
                .when(driverRepository)
                .save(notSavedDriver);
        doReturn(response)
                .when(driverMapper)
                .fromEntityToResponse(savedDriver);

        DriverResponse actual = driverService.createDriver(request);

        verify(driverRepository).existsByNumber(DEFAULT_NUMBER);
        verify(driverRepository).existsByPhone(DEFAULT_PHONE);
        verify(driverRepository).existsByEmail(DEFAULT_EMAIL);
        verify(driverRepository).save(notSavedDriver);
        verify(driverMapper).fromEntityToResponse(savedDriver);
        verify(driverMapper).fromRequestToEntity(request);
        assertThat(actual).isEqualTo(response);

    }

    @Test()
    void createDriverWhenCarNumberIsNotUnique() {
        DriverRequest request = getDriverRequest();

        doReturn(true)
                .when(driverRepository)
                .existsByNumber(request.getNumber());

        assertThrows(
                CarNumberAlreadyExistException.class,
                () -> driverService.createDriver(request)
        );
    }

    @Test()
    void createDriverWhenPhoneIsNotUnique() {
        DriverRequest request = getDriverRequest();

        doReturn(false)
                .when(driverRepository)
                .existsByNumber(request.getNumber());
        doReturn(true)
                .when(driverRepository)
                .existsByPhone(request.getPhone());

        assertThrows(
                PhoneAlreadyExistException.class,
                () -> driverService.createDriver(request)
        );
    }

    @Test()
    void createDriverWhenEmailIsNotUnique() {
        DriverRequest request = getDriverRequest();

        doReturn(false)
                .when(driverRepository)
                .existsByNumber(request.getNumber());
        doReturn(false)
                .when(driverRepository)
                .existsByPhone(request.getPhone());
        doReturn(true)
                .when(driverRepository)
                .existsByEmail(request.getEmail());

        assertThrows(
                EmailAlreadyExistException.class,
                () -> driverService.createDriver(request)
        );
    }

    @Test
    void deleteDriverWhenDriverExist() {
        Driver driver = getDefaultDriver();

        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DEFAULT_ID);
        doNothing()
                .when(driverRepository)
                .delete(driver);

        driverService.deleteDriver(DEFAULT_ID);

        verify(driverRepository).findById(DEFAULT_ID);
        verify(driverRepository).delete(driver);

    }

    @Test
    void updateDriverWhenDriverExistAndDataIsUnique() {
        DriverRequest driverUpdateRequest = getDriverUpdateRequest();
        Driver driver = getDefaultDriver();
        DriverResponse updateDriverResponse = getUpdateDriverResponse();
        Driver updateDriver = getUpdateDriver();


        doReturn(Optional.of(driver))
                .when(driverRepository)
                .findById(DEFAULT_ID);

        doReturn(false)
                .when(driverRepository)
                .existsByEmail(UPDATE_EMAIL);
        doReturn(false)
                .when(driverRepository)
                .existsByPhone(UPDATE_PHONE);
        doReturn(false)
                .when(driverRepository)
                .existsByNumber(UPDATE_NUMBER);
        doReturn(updateDriverResponse)
                .when(driverMapper)
                .fromEntityToResponse(updateDriver);
        doReturn(updateDriver)
                .when(driverMapper)
                .fromRequestToEntity(driverUpdateRequest);
        doReturn(updateDriver)
                .when(driverRepository)
                .save(updateDriver);

        driverService.updateDriver(DEFAULT_ID, driverUpdateRequest);

        verify(driverRepository).existsByNumber(UPDATE_NUMBER);
        verify(driverRepository).existsByPhone(UPDATE_PHONE);
        verify(driverRepository).existsByEmail(UPDATE_EMAIL);
        verify(driverRepository).save(updateDriver);
        verify(driverRepository).findById(DEFAULT_ID);

    }

    @Test
    void findAllAvailableDrivers() {

        DriverListResponse expected = getDriverListResponse();
        System.out.println(expected);
        List<Driver> driverList = getDriverList();


        doReturn(driverList)
                .when(driverRepository)
                .getAllByAvailable(true);
        doReturn(expected.getDriverResponseList().get(0))
                .when(driverMapper)
                .fromEntityToResponse(driverList.get(0));
        doReturn(expected.getDriverResponseList().get(1))
                .when(driverMapper)
                .fromEntityToResponse(driverList.get(1));

        DriverListResponse actual = driverService.getAvailableDrivers();

        verify(driverRepository).getAllByAvailable(true);
        verify(driverMapper).fromEntityToResponse(driverList.get(0));
        verify(driverMapper).fromEntityToResponse(driverList.get(1));

        assertThat(actual.getDriverResponseList()).isEqualTo(expected.getDriverResponseList());

    }

    @Test
    void getDriverPage_ReturnsPageResponse_WhenValidParams() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGINATION_PAGE - 1, DEFAULT_PAGINATION_SIZE);
        Page<Driver> driversPage = mock(Page.class);
        List<Driver> driversList = getDriverList();
        when(driversPage.getContent()).thenReturn(driversList);
        when(driversPage.getTotalElements()).thenReturn((long) driversList.size());
        when(driverRepository.findAll(any(PageRequest.class))).thenReturn(driversPage);

        DriverPageResponse pageResponse = driverService.getDriverPage(DEFAULT_PAGINATION_PAGE, DEFAULT_PAGINATION_SIZE, null);

        assertNotNull(pageResponse);
        assertEquals(driversList.size(), pageResponse.getTotalElements());
        assertEquals(DEFAULT_PAGINATION_PAGE, pageResponse.getTotalPages());
    }


}
