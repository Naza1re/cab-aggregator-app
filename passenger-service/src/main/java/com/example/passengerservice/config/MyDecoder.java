package com.example.passengerservice.config;

import com.example.passengerservice.exception.NotFoundException;
import com.example.passengerservice.exception.RatingException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class MyDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = FeignException.errorStatus(s, response);
        int status = response.status();
        String[] responseMessageSplit = exception.getMessage().split("\"message\"");
        String[] exMessageSplit = responseMessageSplit[responseMessageSplit.length - 1].split("\"");
        String exMessage = exMessageSplit[exMessageSplit.length - 2];
        switch (status) {
            case 400:
                return new RatingException(exMessage);
            case 404:
                return new NotFoundException(exMessage);
        }
        if (status >= 500) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    (Long) null,
                    response.request());
        }
        return exception;
    }
}