package com.example.end2end.util;

import com.example.end2end.dto.request.RideRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class End2EndUtilTest {

    public final static Long PASSENGER_ID = 12L;
    public final static String PICK_UP_ADDRESS = "Frunze 33";
    public final static String DROP_OFF_ADDRESS = "Moscow 33";
    public final static String INSTRUCTIONS = "We have a pet";



    public static RideRequest getRideRequest(Long passengerId) {
        return RideRequest.builder()
                .passengerId(passengerId)
                .pickUpAddress(PICK_UP_ADDRESS)
                .dropOffAddress(DROP_OFF_ADDRESS)
                .instructions(INSTRUCTIONS)
                .build();
    }

}
