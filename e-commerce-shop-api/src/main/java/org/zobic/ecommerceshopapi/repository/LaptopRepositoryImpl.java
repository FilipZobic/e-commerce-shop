package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Laptop;

import java.util.Optional;
import java.util.UUID;

@Repository
public class LaptopRepositoryImpl implements LaptopRepository{

  private LaptopRepositoryPostgreSql repositoryPostgreSql;

  public LaptopRepositoryImpl(LaptopRepositoryPostgreSql repositoryPostgreSql) {
    this.repositoryPostgreSql = repositoryPostgreSql;
  }

  @Override
  public Laptop saveLaptop(Laptop laptop) {
    return this.repositoryPostgreSql.save(laptop);
  }

  @Override
  public void deleteLaptop(UUID id) {
    this.repositoryPostgreSql.deleteById(id);
  }

  @Override
  public Optional<Laptop> findLaptopById(UUID id) {
    return this.repositoryPostgreSql.findById(id);
  }

  @Override
  public Iterable<Laptop> findAll() {
    return this.repositoryPostgreSql.findAll();
  }

  @Override
  public Page<Laptop> findAllPaging(Pageable pageable, String productNameOrId, Double minPrice, Double maxPrice, UUID manufacturerId, Boolean shouldShowOutOfStock) {
    return this.repositoryPostgreSql.findAllPaging(pageable, productNameOrId, minPrice, maxPrice, manufacturerId, shouldShowOutOfStock);
  }
}
