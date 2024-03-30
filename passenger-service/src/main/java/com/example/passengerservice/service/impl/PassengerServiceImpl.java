package com.example.passengerservice.service.impl;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.request.RatingRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.exception.*;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.security.User;
import com.example.passengerservice.service.PassengerService;
import com.example.passengerservice.service.RatingService;
import com.example.passengerservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.example.passengerservice.security.SecurityConstants.*;
import static com.example.passengerservice.util.ConstantsMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final RatingService ratingService;
    private final PassengerMapper passengerMapper;

    @Override
    @Cacheable(cacheNames = "passenger",key = "#id")
    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = getOrThrow(id);
        log.info(String.format(GET_PASSENGER_BY_ID, id));
        return passengerMapper.fromEntityToResponse(passenger);
    }

    @Override
    @Cacheable(cacheNames = "passenger")
    public PassengerListResponse getAllPassengers() {
        List<PassengerResponse> listOfPassengers = passengerRepository.findAll().stream()
                .map(passengerMapper::fromEntityToResponse)
                .toList();
        log.info(GET_PASSENGER_LIST);
        return new PassengerListResponse(listOfPassengers);
    }

    @Transactional
    @Override
    @CacheEvict(cacheNames = "restaurant", allEntries = true)
    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = passengerMapper.fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        ratingService.createPassengerRecord(RatingRequest.builder()
                .id(savedPassenger.getId())
                .build());
        log.info(String.format(CREATE_PASSENGER, savedPassenger.getId()));
        return passengerMapper.fromEntityToResponse(savedPassenger);
    }

    @Override
    @CacheEvict(cacheNames = "restaurant", allEntries = true)
    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {

        Passenger passenger = getOrThrow(id);

        preUpdateEmailCheck(passenger, passengerRequest.getEmail());
        preUpdatePhoneCheck(passenger, passengerRequest.getPhone());

        passenger = passengerMapper.fromRequestToEntity(passengerRequest);
        passenger.setId(id);
        log.info(String.format(UPDATE_PASSENGER, id));
        return passengerMapper.fromEntityToResponse(passengerRepository.save(passenger));

    }

    @Override
    @CacheEvict(cacheNames = "restaurant", allEntries = true)
    public PassengerResponse deletePassenger(Long id) {
        Passenger passenger = getOrThrow(id);
        passengerRepository.delete(passenger);

        ratingService.deletePassengerRecord(id);
        log.info(String.format(DELETE_PASSENGER, id));
        return passengerMapper.fromEntityToResponse(passenger);

    }

    private void preUpdateEmailCheck(Passenger passenger, String email) {

        if (!passenger.getEmail().equals(email)) {
            checkEmailExist(email);
        }
    }

    private void preUpdatePhoneCheck(Passenger passenger, String phone) {

        if (!passenger.getPhone().equals(phone)) {
            checkPhoneExist(phone);
        }
    }

    private void checkEmailExist(String email) {

        if (passengerRepository.existsByEmail(email)) {
            log.info(String.format(PASSENGER_WITH_MAIL_ALREADY_EXIST, email));
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_EMAIL_ALREADY_EXIST, email));
        }
    }

    private void checkPhoneExist(String phone) {

        if (passengerRepository.existsByPhone(phone)) {
            log.info(String.format(PASSENGER_WITH_PHONE_ALREADY_EXIST, phone));
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_PHONE_ALREADY_EXIST, phone));
        }
    }

    private PageRequest getPageRequest(int page, int size, String orderBy) {
        if (page < 1 || size < 1) {
            throw new PaginationParamException(String.format(ExceptionMessages.PAGINATION_FORMAT_EXCEPTION));
        }

        PageRequest pageRequest;
        if (orderBy == null) {
            pageRequest = PageRequest.of(page - 1, size);
        } else {
            validateSortingParameter(orderBy);
            pageRequest = PageRequest.of(page - 1, size, Sort.by(orderBy));
        }

        return pageRequest;
    }

    private void validateSortingParameter(String orderBy) {
        Arrays.stream(PassengerResponse.class.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> field.equals(orderBy))
                .findFirst()
                .orElseThrow(() -> new SortTypeException(ExceptionMessages.INVALID_TYPE_OF_SORT));
    }

    @Override
    public PassengerPageResponse getPassengerPage(int page, int size, String orderBy) {

        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Passenger> passengersPage = passengerRepository.findAll(pageRequest);

        List<Passenger> retrievedPassengers = passengersPage.getContent();
        long total = passengersPage.getTotalElements();

        List<PassengerResponse> passengers = retrievedPassengers.stream()
                .map(passengerMapper::fromEntityToResponse)
                .toList();
        log.info(GET_PASSENGER_PAGE);
        return PassengerPageResponse.builder()
                .passengerList(passengers)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    private Passenger getOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION, id)));
    }

    public PassengerRequest getPassengerRequestFromOauth2User(OAuth2User oAuth2User) {
        return PassengerRequest.builder()
                .name(oAuth2User.getAttribute(NAME))
                .surname(oAuth2User.getAttribute(SURNAME))
                .email(oAuth2User.getAttribute(EMAIL))
                .phone(oAuth2User.getAttribute(PHONE))
                .build();
    }

    public User extractUserInfo(Jwt jwt) {
        return User.builder()
                .phone(jwt.getClaim(PHONE))
                .surname(jwt.getClaim(FAMILY_NAME))
                .name(jwt.getClaim(GIVEN_NAME))
                .id(UUID.fromString(jwt.getClaim(ID)))
                .email(jwt.getClaim(EMAIL))
                .username(jwt.getClaim(USERNAME))
                .build();
    }

}
