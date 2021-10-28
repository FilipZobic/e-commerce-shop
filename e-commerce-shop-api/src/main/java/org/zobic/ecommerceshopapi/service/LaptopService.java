package org.zobic.ecommerceshopapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zobic.ecommerceshopapi.dto.CreateLaptopDto;
import org.zobic.ecommerceshopapi.dto.UpdateLaptopDto;
import org.zobic.ecommerceshopapi.exception.FormatNotSupported;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Laptop;

import java.io.IOException;
import java.util.UUID;

public interface LaptopService {

  Laptop createLaptop(CreateLaptopDto laptop) throws ResourceNotFoundException, FormatNotSupported, IOException;

  Laptop updateLaptop(UpdateLaptopDto laptop, UUID id) throws ResourceNotFoundException, FormatNotSupported, IOException;

  void deleteLaptop(UUID id);

  Laptop findLaptopById(UUID id) throws ResourceNotFoundException;

  Iterable<Laptop> findAll();

  Page<Laptop> findAllPaging(Pageable pageable, String productNameOrId, Double minPrice, Double maxPrice, UUID manufacturerId, Boolean shouldShowOutOfStock);
}
