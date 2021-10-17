package org.zobic.ecommerceshopapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.ManufacturerDto;
import org.zobic.ecommerceshopapi.dto.ManufacturerValidationDto;
import org.zobic.ecommerceshopapi.exception.EntityHasRelationShipsException;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Manufacturer;
import org.zobic.ecommerceshopapi.service.ManufacturerService;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ManufacturerController {

  private ManufacturerService service;

  public ManufacturerController(ManufacturerService service) {
    this.service = service;
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("api/manufacturers")
  public ResponseEntity<List<ManufacturerDto>> getAll() {
    List<ManufacturerDto> manufacturerDtoArrayList = new ArrayList<>();
    service.findAll().forEach(s -> {
      manufacturerDtoArrayList.add(
        ManufacturerDto.builder()
          .id(s.getId())
          .name(s.getName())
          .numberOfProducts(s.getNumberOfProducts())
          .build()
      );
    });
    return new ResponseEntity<>(manufacturerDtoArrayList, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("api/manufacturers")
  public ResponseEntity<ManufacturerDto> createNewManufacturer(@Valid @RequestBody ManufacturerDto manufacturerDto) {
    Manufacturer m = this.service.saveManufacturer(manufacturerDto);

    return new ResponseEntity<>(ManufacturerDto.builder()
      .id(m.getId())
      .name(m.getName())
      .numberOfProducts(m.getNumberOfProducts())
      .build(), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_USER')")
  @PostMapping("api/manufacturers/isNameTaken")
  public ResponseEntity<Map<String, Object>> isNameTaken(@Valid @RequestBody ManufacturerValidationDto manufacturerDto) {
    Optional<Manufacturer> manufacturer = this.service.findManufacturerByName(manufacturerDto.getName());
    Map<String, Object> response = new HashMap<>();
    response.put("isManufacturerNameTaken", manufacturer.isPresent());
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("api/manufacturers/{id}")
  public ResponseEntity<ManufacturerDto> updateManufacturer(@Valid @RequestBody ManufacturerDto manufacturerDto, @PathVariable(name = "id")UUID manufacturerId) throws ResourceNotFoundException {
    Manufacturer uM = this.service.updateManufacturer(manufacturerDto, manufacturerId);
    return new ResponseEntity<>(ManufacturerDto.builder()
      .id(uM.getId())
      .name(uM.getName())
      .numberOfProducts(uM.getNumberOfProducts())
      .build(), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @DeleteMapping("api/manufacturers/{id}")
  public ResponseEntity<ManufacturerDto> deleteManufacturer(@PathVariable(name = "id")UUID manufacturerId) throws ResourceNotFoundException, EntityHasRelationShipsException {
    this.service.deleteManufacturer(manufacturerId);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
