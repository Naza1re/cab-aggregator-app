package com.example.passengerservice.config;

import com.example.passengerservice.exception.FeignClientException;
import com.example.passengerservice.exception.NotFoundException;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MyDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        String[] responseMessageSplit = exception.getMessage().split("\"message\"");
        String[] exMessageSplit = responseMessageSplit[responseMessageSplit.length - 1].split("\"");
        String exMessage = exMessageSplit[exMessageSplit.length - 2];
        if (status == 400) {
            return new FeignClientException(exMessage);
        }
        if (status == 404) {
            return new NotFoundException(exMessage);
        }
        return exception;
    }
}