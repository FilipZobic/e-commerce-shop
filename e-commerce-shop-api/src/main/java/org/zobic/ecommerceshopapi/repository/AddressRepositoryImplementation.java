package org.zobic.ecommerceshopapi.repository;

import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.model.Address;

@Component
public class AddressRepositoryImplementation implements AddressRepository {

    private AddressRepositoryPostgreSql addressRepository;

  public AddressRepositoryImplementation(AddressRepositoryPostgreSql addressRepositoryPostgreSql) {

    this.addressRepository = addressRepositoryPostgreSql;
  }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }
}
