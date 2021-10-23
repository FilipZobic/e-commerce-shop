package org.zobic.ecommerceshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.CreateLaptopDto;
import org.zobic.ecommerceshopapi.dto.LaptopResponseDto;
import org.zobic.ecommerceshopapi.dto.ManufacturerDto;
import org.zobic.ecommerceshopapi.dto.UpdateLaptopDto;
import org.zobic.ecommerceshopapi.exception.FormatNotSupported;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Laptop;
import org.zobic.ecommerceshopapi.model.Manufacturer;
import org.zobic.ecommerceshopapi.repository.LaptopRepository;
import org.zobic.ecommerceshopapi.repository.LaptopRepositoryPostgreSql;
import org.zobic.ecommerceshopapi.service.FileSystemService;
import org.zobic.ecommerceshopapi.service.LaptopService;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class LaptopController {

  @Autowired
  FileSystemService fileSystemService;

  @Autowired
  LaptopService laptopService;

  @Autowired
  LaptopRepositoryPostgreSql laptopRepositoryPostgreSql;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PostMapping("/api/products/laptops")
  public ResponseEntity<?> createProduct(@Valid @RequestBody CreateLaptopDto laptopDto) throws IOException, FormatNotSupported, ResourceNotFoundException {
    Laptop laptop = this.laptopService.createLaptop(laptopDto);
    Manufacturer manufacturer = laptop.getManufacturer();
    LaptopResponseDto laptopResponseDto = LaptopResponseDto.builder()
      .name(laptop.getName())
      .diagonal(laptop.getDiagonal())
      .price(laptop.getPrice())
      .ram(laptop.getRam().getSize())
      .panelType(laptop.getPanelType())
      .stock(laptop.getStock())
      .id(laptop.getId())
      .manufacturer(ManufacturerDto.
        builder()
        .id(manufacturer.getId())
        .name(manufacturer.getName())
        .numberOfProducts(manufacturer.getNumberOfProducts() + 1)
        .build())
      .build();

    return new ResponseEntity<>(laptopResponseDto, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/api/products/laptops/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
    this.laptopService.deleteLaptop(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PutMapping("/api/products/laptops/{id}")
  public ResponseEntity<?> updateProduct(@Valid @RequestBody UpdateLaptopDto laptopDto, @PathVariable UUID id) throws IOException, FormatNotSupported, ResourceNotFoundException {
    Laptop laptop = this.laptopService.updateLaptop(laptopDto, id);
    Manufacturer manufacturer = laptop.getManufacturer();
    LaptopResponseDto laptopResponseDto = LaptopResponseDto.builder()
      .name(laptop.getName())
      .diagonal(laptop.getDiagonal())
      .price(laptop.getPrice())
      .ram(laptop.getRam().getSize())
      .panelType(laptop.getPanelType())
      .stock(laptop.getStock())
      .id(laptop.getId())
      .manufacturer(ManufacturerDto.
        builder()
        .id(manufacturer.getId())
        .name(manufacturer.getName())
        .numberOfProducts(manufacturer.getNumberOfProducts() + 1)
        .build())
      .build();

    return new ResponseEntity<>(laptopResponseDto, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @GetMapping("/api/products/laptops")
  public ResponseEntity<?> getAllProductsPaging(
    @RequestParam(required = false)UUID manufacturerId,
    @RequestParam(required = false)String productName,
    @RequestParam(required = false)Double minPrice,
    @RequestParam(required = false)Double maxPrice,
    @RequestParam(required = false, defaultValue = "price")String sortByProperty,
    @RequestParam(required = false, defaultValue = "true")Boolean shouldReturnImage,
    @RequestParam Integer size,
    @RequestParam Integer page
    )
  {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortByProperty));
    Boolean shouldShowOutOfStock = UtilitySecurity.userHasAdminRole();

    Page<Laptop> laptopList = this.laptopService.findAllPaging(pageable, productName, minPrice, maxPrice, manufacturerId, shouldShowOutOfStock);

    Page<LaptopResponseDto> responseDtos = laptopList.map(laptop ->
      LaptopResponseDto.builder()
        .name(laptop.getName())
        .diagonal(laptop.getDiagonal())
        .price(laptop.getPrice())
        .ram(laptop.getRam().getSize())
        .panelType(laptop.getPanelType())
        .stock(laptop.getStock())
        .id(laptop.getId())
        .image((shouldReturnImage ? fileSystemService.getFile(Path.of(laptop.getCoverImagePath())) : null))
        .manufacturer(ManufacturerDto.
          builder()
          .id(laptop.getManufacturer().getId())
          .name(laptop.getManufacturer().getName())
          .numberOfProducts(laptop.getManufacturer().getNumberOfProducts() + 1)
          .build())
        .build());

    Integer priceCeiling = laptopRepositoryPostgreSql.findMaxPrice();
    if (priceCeiling == null) {
      priceCeiling = 0;
    }
    HashMap<String, Object> response = new HashMap<>();
    response.put("page", responseDtos);
    response.put("priceCeiling", priceCeiling);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping(value = "/api/products/images/{filename}",
    produces = MediaType.IMAGE_JPEG_VALUE)
  public @ResponseBody byte[] getImage(@PathVariable String filename) throws IOException {
    return Base64.getDecoder().decode(fileSystemService.getFile(filename));
  }
}
