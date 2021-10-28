package org.zobic.ecommerceshopapi.service;

import org.zobic.ecommerceshopapi.dto.CountryDto;
import org.zobic.ecommerceshopapi.exception.CountryNotFoundException;

import java.util.List;

public interface CountryService {
  List<CountryDto> requestAllCountries() throws Exception;

  CountryDto requestCountry(String alpha2code) throws CountryNotFoundException;

  String getUpdatedCountryCode(String oldCountryCode, String newCountryCode);
}
