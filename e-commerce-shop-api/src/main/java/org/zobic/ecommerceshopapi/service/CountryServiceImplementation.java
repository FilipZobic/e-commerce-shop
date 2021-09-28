package org.zobic.ecommerceshopapi.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.zobic.ecommerceshopapi.dto.CountryDto;
import org.zobic.ecommerceshopapi.exception.CountryNotFoundException;

import java.util.List;

@Service
public class CountryServiceImplementation implements CountryService {

  private final RestTemplate restTemplate;
  private final String uri = "https://restcountries.com/v2/";

  public CountryServiceImplementation(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<CountryDto> requestAllCountries() throws Exception {
    String uriAll = this.uri + "all";
    ResponseEntity<List<CountryDto>> response = restTemplate.exchange(
      uriAll,
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<CountryDto>>(){});
    List<CountryDto> countriesDto = response.getBody();
    return countriesDto;
  }

  @Override
  public CountryDto requestCountry(String alpha2code) throws CountryNotFoundException {
    String uriAlpha2 = this.uri + "alpha/" + alpha2code;

    try {
      ResponseEntity<CountryDto> response = restTemplate.exchange(
        uriAlpha2,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<CountryDto>(){});
      return response.getBody();
    } catch (HttpClientErrorException.NotFound e) {
      throw new CountryNotFoundException();
    }
  }

  @Override
  public String getUpdatedCountryCode(String oldCountryCode, String newCountryCode) {
    if (!oldCountryCode.equals(newCountryCode)) {
      return this.requestCountry(newCountryCode).getAlpha2Code();
    }
    return oldCountryCode;
  }
}
