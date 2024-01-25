package com.example.passengerservice.service.impl;

import com.example.passengerservice.dto.request.PassengerRequest;
import com.example.passengerservice.dto.response.PassengerListResponse;
import com.example.passengerservice.dto.response.PassengerPageResponse;
import com.example.passengerservice.dto.response.PassengerResponse;
import com.example.passengerservice.exception.*;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import com.example.passengerservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;

    private PassengerResponse fromEntityToResponse(Passenger passenger){
        return modelMapper.map(passenger, PassengerResponse.class);
    }

    private Passenger fromRequestToEntity(PassengerRequest passengerRequest){
        return modelMapper.map(passengerRequest, Passenger.class);
    }

    public PassengerResponse getPassengerById(Long id) {
        Passenger passenger = getOrThrow(id);
        return fromEntityToResponse(passenger);

    }

    public PassengerListResponse getAllPassengers(){
          List<PassengerResponse> listOfPassengers = passengerRepository.findAll().stream()
                .map(this::fromEntityToResponse)
                .toList();
        return new PassengerListResponse(listOfPassengers);
    }

    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {

        checkEmailExist(passengerRequest.getEmail());
        checkPhoneExist(passengerRequest.getPhone());

        Passenger passenger = fromRequestToEntity(passengerRequest);
        Passenger savedPassenger = passengerRepository.save(passenger);

        return fromEntityToResponse(savedPassenger);
    }

    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {

        Passenger passenger = getOrThrow(id);

        preUpdateEmailCheck(passenger,passengerRequest.getEmail());
        preUpdatePhoneCheck(passenger,passengerRequest.getPhone());

        passenger = fromRequestToEntity(passengerRequest);
        passenger.setId(id);
        return fromEntityToResponse(passengerRepository.save(passenger));

    }

    public PassengerResponse deletePassenger(Long id) {
       Passenger passenger = getOrThrow(id);
            passengerRepository.delete(passenger);
            return fromEntityToResponse(passenger);

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
            throw new EmailAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_EMAIL_ALREADY_EXIST,email));
        }
    }

    private void checkPhoneExist(String phone) {

        if (passengerRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistException(String.format(ExceptionMessages.PASSENGER_WITH_PHONE_ALREADY_EXIST,phone));
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
        List<String> fieldNames = Arrays.stream(PassengerResponse.class.getDeclaredFields())
                .map(Field::getName)
                .filter(field -> !field.equals(orderBy))
                .toList();

        if (fieldNames.isEmpty()) {
            throw new SortTypeException(ExceptionMessages.INVALID_TYPE_OF_SORT);
        }
    }
    public PassengerPageResponse getPassengerPage(int page, int size, String orderBy) {

        PageRequest pageRequest = getPageRequest(page, size, orderBy);
        Page<Passenger> passengersPage = passengerRepository.findAll(pageRequest);

        List<Passenger> retrievedPassengers = passengersPage.getContent();
        long total = passengersPage.getTotalElements();

        List<PassengerResponse> passengers = retrievedPassengers.stream()
                .map(this::fromEntityToResponse)
                .toList();

        return PassengerPageResponse.builder()
                .passengerList(passengers)
                .totalPages(page)
                .totalElements(total)
                .build();
    }

    private Passenger getOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(()-> new PassengerNotFoundException(String.format(ExceptionMessages.PASSENGER_NOT_FOUND_EXCEPTION,id)));
    }

}
