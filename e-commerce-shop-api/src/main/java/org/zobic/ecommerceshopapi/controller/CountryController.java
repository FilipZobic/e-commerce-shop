package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zobic.ecommerceshopapi.dto.CountryDto;
import org.zobic.ecommerceshopapi.service.CountryService;
import org.zobic.ecommerceshopapi.service.CountryServiceImplementation;

import java.util.List;

@RestController
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryServiceImplementation countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/api/countries")
    public ResponseEntity<List<CountryDto>> getAllCountries () throws Exception {
        return new ResponseEntity<>(countryService.requestAllCountries(), HttpStatus.OK);
    }
}
