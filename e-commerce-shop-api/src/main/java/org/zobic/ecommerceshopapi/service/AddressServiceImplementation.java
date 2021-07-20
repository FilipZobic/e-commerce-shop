package org.zobic.ecommerceshopapi.service;

import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.AddressDto;
import org.zobic.ecommerceshopapi.dto.CountryDto;
import org.zobic.ecommerceshopapi.model.Address;
import org.zobic.ecommerceshopapi.repository.AddressRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class AddressServiceImplementation implements AddressService{

    private AddressRepository repository;

    private CountryService countryService;

    public AddressServiceImplementation(AddressRepository repository, CountryServiceImplementation countryService) {
        this.repository = repository;
        this.countryService = countryService;
    }

    @Transactional
    @Override
    public Address save(AddressDto addressDto) throws Exception {

      if (addressDto == null) {
        throw new Exception("Address is null");
      }

      String cityName = addressDto.getCityName();
        String cityZipCode = addressDto.getZipCode();
        String addressValue =  addressDto.getAddressValue();
        String alpha2code = addressDto.getCountryAlpha2Code();

        CountryDto countryDto = this.countryService.requestCountry(alpha2code);

        Address address = new Address(addressValue,cityZipCode,cityName,countryDto.getAlpha2Code());

        return this.repository.save(address);
    }

    @Transactional
    @Override
    public Address update(UUID addressId, AddressDto addressDto, Address address) throws Exception {

        if (addressDto == null) {
          throw new Exception("Address is null");
        }

        if (address == null || addressId == null) {
          return this.save(addressDto);
        }

        String countryCode = countryService.getUpdatedCountryCode(address.getCountryCode(), addressDto.getCountryAlpha2Code());

        Address newAddress = new Address(addressId, addressDto.getAddressValue(),addressDto.getZipCode(),addressDto.getCityName(), countryCode);

        return this.repository.save(newAddress);
    }
}
