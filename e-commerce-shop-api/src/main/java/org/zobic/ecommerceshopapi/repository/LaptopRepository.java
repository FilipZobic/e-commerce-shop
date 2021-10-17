package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zobic.ecommerceshopapi.model.Laptop;

import java.util.Optional;
import java.util.UUID;

public interface LaptopRepository {

  Laptop saveLaptop(Laptop laptop);

  void deleteLaptop(UUID id);

  Optional<Laptop> findLaptopById(UUID id);

  Iterable<Laptop> findAll();

    Page<Laptop> findAllPaging(Pageable pageable, String productNameOrId, Double minPrice, Double maxPrice, UUID manufacturerId, Boolean shouldShowOutOfStock);
}
