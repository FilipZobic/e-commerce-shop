package org.zobic.ecommerceshopapi.cache;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.CountryDto;

import java.util.List;

@Service
public class CountryCache {

  private final String REDIS_DB_NAME = "COUNTRIES";

  private final String COUNTRIES_LIST = "COUNTRIES_LIST";

  private HashOperations hashOperations;

  private RedisTemplate redisTemplate;

  public CountryCache(RedisTemplate redisTemplate) {
    this.hashOperations = redisTemplate.opsForHash();
    this.redisTemplate = redisTemplate;
  }

  public void putCountriesIntoCache(List<CountryDto> countryDtoList) {
    for (CountryDto countryDto : countryDtoList) {
      hashOperations.put(REDIS_DB_NAME, countryDto.getAlpha2Code(), countryDto);
    }
  }

  public List<CountryDto> getCountriesFromCache() {
    return hashOperations.values(REDIS_DB_NAME);
  }

  public CountryDto getCountryFromCache(String alpha2code) {
    return (CountryDto) hashOperations.get(REDIS_DB_NAME, alpha2code);
  }
}
