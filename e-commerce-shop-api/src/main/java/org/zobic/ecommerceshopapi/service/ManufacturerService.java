package org.zobic.ecommerceshopapi.service;

import org.zobic.ecommerceshopapi.dto.ManufacturerDto;
import org.zobic.ecommerceshopapi.exception.EntityHasRelationShipsException;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Manufacturer;

import java.util.List;
import java.util.UUID;

public interface ManufacturerService {

  List<Manufacturer> findAll();

  Manufacturer saveManufacturer(ManufacturerDto manufacturerDto);

  Manufacturer findManufacturerById(UUID id) throws ResourceNotFoundException;

  void deleteManufacturer(UUID id) throws ResourceNotFoundException, EntityHasRelationShipsException;

  Manufacturer updateManufacturer(ManufacturerDto manufacturerDto, UUID id) throws ResourceNotFoundException;
}
