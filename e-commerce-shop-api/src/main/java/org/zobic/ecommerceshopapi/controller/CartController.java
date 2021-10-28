package org.zobic.ecommerceshopapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.zobic.ecommerceshopapi.dto.LaptopResponseDto;
import org.zobic.ecommerceshopapi.dto.ManufacturerDto;
import org.zobic.ecommerceshopapi.exception.ResourceNotFoundException;
import org.zobic.ecommerceshopapi.model.CartItem;
import org.zobic.ecommerceshopapi.model.Laptop;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.repository.LaptopRepositoryPostgreSql;
import org.zobic.ecommerceshopapi.repository.UserRepository;
import org.zobic.ecommerceshopapi.service.FileSystemService;
import org.zobic.ecommerceshopapi.service.LaptopService;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.util.Utility;
import org.zobic.ecommerceshopapi.util.UtilitySecurity;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CartController {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  LaptopService laptopService;

  @Autowired
  LaptopRepositoryPostgreSql laptopRepositoryPostgreSql;

  @Autowired
  FileSystemService fileSystemService;

  @GetMapping("/api/products/laptops/cart")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> getAllProductsInCart(
      @RequestParam(required = false) UUID userId,
      @RequestParam(required = false, defaultValue = "true")Boolean shouldReturnImage) throws Exception {

    User user;
    if (UtilitySecurity.userHasAdminRole() && userId != null) {
      user = this.userService.findUserById(userId);
    } else {
      UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      user = userService.findUserByEmail(authentication.getUsername()).orElseThrow(() -> {
        log.warn("User {} has authenticated but does not exist in database", authentication.getUsername());
        return new ResourceNotFoundException("User does not exist");
      });
    }

    List<LaptopResponseDto> laptopResponseDtos = laptopRepositoryPostgreSql.findAllByUserId(user.getId()).stream().map(a -> {
      var laptop = a.getLaptop();
      var laptopCarItem = a.getCartItem();

      return LaptopResponseDto.builder()
        .name(laptop.getName())
        .diagonal(laptop.getDiagonal())
        .price(laptop.getPrice())
        .ram(laptop.getRam().getSize())
        .panelType(laptop.getPanelType())
        .stock(laptop.getStock())
        .id(laptop.getId())
        .cartInfo(laptopCarItem)
        .image((shouldReturnImage ? fileSystemService.getFile(Path.of(laptop.getCoverImagePath())) : null))
        .manufacturer(ManufacturerDto.
          builder()
          .id(laptop.getManufacturer().getId())
          .name(laptop.getManufacturer().getName())
          .numberOfProducts(laptop.getManufacturer().getNumberOfProducts() + 1)
          .build())
        .build();}).collect(Collectors.toList());


    return new ResponseEntity<>(laptopResponseDtos, HttpStatus.OK);
  }

  @PostMapping("/api/products/laptops/cart")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> addToCart(@RequestParam UUID laptopId) throws ResourceNotFoundException {

    UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user = userService.findUserByEmail(authentication.getUsername()).orElseThrow(() -> {
      log.warn("User {} has authenticated but does not exist in database", authentication.getUsername());
      return new ResourceNotFoundException("User does not exist");
    });

    // throws exception if it does not exist
    Laptop laptop = laptopService.findLaptopById(laptopId);

    CartItem cartItem = CartItem.builder().id(CartItem.CartItemId.builder().userId(user.getId()).laptopId(laptopId).build()).amount(1).build();

    user.addToCart(cartItem, laptop.getStock());
    this.userRepository.saveUser(user);

    return new ResponseEntity<>(Utility.userToDtoWithCartItems(user), HttpStatus.OK);
  }

  @DeleteMapping("/api/products/laptops/cart")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<?> removeFromCart(@RequestParam UUID laptopId) throws ResourceNotFoundException {

    UserDetails authentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    User user = userService.findUserByEmail(authentication.getUsername()).orElseThrow(() -> {
      log.warn("User {} has authenticated but does not exist in database", authentication.getUsername());
      return new ResourceNotFoundException("User does not exist");
    });

    // throws exception if it does not exist
    Laptop laptop = laptopService.findLaptopById(laptopId);

    CartItem cartItem = CartItem.builder().id(CartItem.CartItemId.builder().userId(user.getId()).laptopId(laptopId).build()).amount(1).build();

    user.removeFromCart(cartItem);
    this.userRepository.saveUser(user);

    return new ResponseEntity<>(Utility.userToDtoWithCartItems(user), HttpStatus.OK);
  }

  //get all products
}
