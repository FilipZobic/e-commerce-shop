package org.zobic.ecommerceshopapi.service;


import org.zobic.ecommerceshopapi.dto.AddressDto;
import org.zobic.ecommerceshopapi.model.Address;

import java.util.UUID;

public interface AddressService {

    Address save(AddressDto addressDto) throws Exception;

    Address update(UUID id, AddressDto addressDto, Address address, Boolean deleted) throws Exception;
}
