package org.zobic.ecommerceshopapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class CountryNotFoundException extends HttpClientErrorException{
    public CountryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Country code does not exist.");
    }
}
