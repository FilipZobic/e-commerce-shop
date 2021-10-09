package org.zobic.ecommerceshopapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.ManufacturerDto;
import org.zobic.ecommerceshopapi.exception.EntityHasRelationShipsException;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Manufacturer;
import org.zobic.ecommerceshopapi.repository.ManufacturerRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ManufacturerServiceImpl implements ManufacturerService{

  private ManufacturerRepository manufacturerRepository;

  public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
    this.manufacturerRepository = manufacturerRepository;
  }

  @Override
  public List<Manufacturer> findAll(){
    return this.manufacturerRepository.getAll();
  }

  @Override
  public Manufacturer saveManufacturer(ManufacturerDto manufacturerDto) {
    Manufacturer manufacturer = Manufacturer.builder().name(manufacturerDto.getName()).build();
    return this.manufacturerRepository.save(manufacturer);
  }

  @Override
  public Manufacturer findManufacturerById(UUID id) throws ResourceNotFoundException {
    return manufacturerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manufacturer with that id is not found"));
  }

  @Override
  public void deleteManufacturer(UUID id) throws ResourceNotFoundException, EntityHasRelationShipsException {
    Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manufacturer with that id is not found"));
    if (manufacturer.hasAnyRelation()) {
      log.warn("ID: {}, Reason: {}",manufacturer.getId(), manufacturer.getReason());
      throw new EntityHasRelationShipsException(manufacturer.getReason());
    } else {
      manufacturerRepository.delete(id);
    }
  }

  @Override
  public Manufacturer updateManufacturer(ManufacturerDto manufacturerDto, UUID id) throws ResourceNotFoundException {
    Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manufacturer with that id is not found"));
    manufacturer.setName(manufacturerDto.getName());
    return this.manufacturerRepository.save(manufacturer);
  }
}
