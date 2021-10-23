package org.zobic.ecommerceshopapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zobic.ecommerceshopapi.dto.CreateLaptopDto;
import org.zobic.ecommerceshopapi.dto.UpdateLaptopDto;
import org.zobic.ecommerceshopapi.exception.FormatNotSupported;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.Laptop;
import org.zobic.ecommerceshopapi.model.Manufacturer;
import org.zobic.ecommerceshopapi.model.Ram;
import org.zobic.ecommerceshopapi.repository.LaptopRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
public class LaptopServiceImpl implements LaptopService {

  private LaptopRepository laptopRepository;

  private FileSystemService fileSystemService;

  private ManufacturerService manufacturerService;

  private RamService ramService;

  private PanelService panelService;

  public LaptopServiceImpl(LaptopRepository laptopRepository, FileSystemService fileSystemService, ManufacturerService manufacturerService, RamService ramService, PanelService panelService) {
    this.laptopRepository = laptopRepository;
    this.fileSystemService = fileSystemService;
    this.manufacturerService = manufacturerService;
    this.ramService = ramService;
    this.panelService = panelService;
  }

  @Transactional
  @Override
  public Laptop createLaptop(CreateLaptopDto laptop) throws ResourceNotFoundException, FormatNotSupported, IOException {

    Manufacturer manufacturer = this.manufacturerService.findManufacturerById(laptop.getManufacturerId());

    boolean panelSupported = this.panelService.isPanelSupported(laptop.getPanelType());

    if (!panelSupported) {
      throw new FormatNotSupported("The panel type is not supported");
    }

    boolean ramValueSupported = ramService.isRamValueSupported(laptop.getRam());

    if (!ramValueSupported) {
      throw new FormatNotSupported("The ram value is not supported");
    }

    String fileName = UUID.randomUUID().toString();
    Path filePath = this.fileSystemService.generateFilePath(fileName);

    Laptop newLaptop = Laptop.builder()
      .name(laptop.getName())
      .manufacturer(manufacturer)
      .diagonal(laptop.getDiagonal())
      .panelType(laptop.getPanelType())
      .price(laptop.getPrice())
      .ram(Ram.of(laptop.getRam()))
      .coverImagePath(filePath.toString())
      .stock(laptop.getStock())
      .build();
    newLaptop = this.laptopRepository.saveLaptop(newLaptop);
    this.fileSystemService.saveFile(laptop.getImage(), fileName);

    return newLaptop;
  }

  @Transactional
  @Override
  public Laptop updateLaptop(UpdateLaptopDto updateLaptopDto, UUID id) throws ResourceNotFoundException, FormatNotSupported, IOException {
    Laptop oldLaptop = this.findLaptopById(id);
    Manufacturer manufacturer = this.manufacturerService.findManufacturerById(updateLaptopDto.getManufacturerId());

    boolean panelSupported = this.panelService.isPanelSupported(updateLaptopDto.getPanelType());

    if (!panelSupported) {
      throw new FormatNotSupported("The panel type is not supported");
    }

    boolean ramValueSupported = ramService.isRamValueSupported(updateLaptopDto.getRam());

    if (!ramValueSupported) {
      throw new FormatNotSupported("The ram value is not supported");
    }


    String newFile = UUID.randomUUID().toString();
    Path newFilePath = this.fileSystemService.generateFilePath(newFile);

    Laptop newLaptop = Laptop.builder()
      .name(updateLaptopDto.getName())
      .manufacturer(manufacturer)
      .diagonal(updateLaptopDto.getDiagonal())
      .panelType(updateLaptopDto.getPanelType())
      .price(updateLaptopDto.getPrice())
      .ram(Ram.of(updateLaptopDto.getRam()))
      .coverImagePath(newFilePath.toString())
      .stock(updateLaptopDto.getStock())
      .build();
    newLaptop.setId(oldLaptop.getId());

    boolean areLaptopsTheSame = oldLaptop.equals(newLaptop);

    boolean areImagesTheSame = this.fileSystemService.compareNewAndOldImage(updateLaptopDto.getImage(), fileSystemService.getFile(Path.of(oldLaptop.getCoverImagePath())));

    if (areImagesTheSame) {
      newLaptop.setCoverImagePath(oldLaptop.getCoverImagePath());
    }

    if (!areLaptopsTheSame || !areImagesTheSame) {
      newLaptop = this.laptopRepository.saveLaptop(newLaptop);
    }

    if (!areImagesTheSame) {
      try {
//        fileSystemService.deleteFile(Path.of(oldLaptop.getCoverImagePath()));
      } catch (Exception e) {
        log.error("Failed deleting old image. message: {}", e.getMessage());
      }
      this.fileSystemService.saveFile(updateLaptopDto.getImage(), newFile);
    }

    return newLaptop;
  }

  @Override
  public void deleteLaptop(UUID id) {
    //check if laptop has any relationship if yes then delete it if laptop has stock of 0 it wont be pulled for the user
    this.laptopRepository.deleteLaptop(id);
  }

  @Override
  public Laptop findLaptopById(UUID id) throws ResourceNotFoundException {
    return this.laptopRepository.findLaptopById(id).orElseThrow(()-> new ResourceNotFoundException("Laptop not found"));
  }

  @Override
  public Iterable<Laptop> findAll() {
    return this.laptopRepository.findAll();
  }

  @Override
  public Page<Laptop> findAllPaging(Pageable pageable, String productNameOrId, Double minPrice, Double maxPrice, UUID manufacturerId, Boolean shouldShowOutOfStock) {
    return this.laptopRepository.findAllPaging(pageable, productNameOrId, minPrice, maxPrice, manufacturerId,shouldShowOutOfStock);
  }
}
