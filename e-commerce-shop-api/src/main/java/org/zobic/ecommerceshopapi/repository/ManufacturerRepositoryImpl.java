package org.zobic.ecommerceshopapi.repository;

import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Manufacturer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ManufacturerRepositoryImpl implements ManufacturerRepository{

  private ManufacturerRepositoryPostgreSql repositoryPostgreSql;

  public ManufacturerRepositoryImpl(ManufacturerRepositoryPostgreSql repositoryPostgreSql) {
    this.repositoryPostgreSql = repositoryPostgreSql;
  }

  @Override
  public List<Manufacturer> getAll() {
    return repositoryPostgreSql.findAll();
  }

  @Override
  public Optional<Manufacturer> findById(UUID id) {
    return repositoryPostgreSql.findById(id);
  }

  @Override
  public Manufacturer save(Manufacturer manufacturer) {
    return repositoryPostgreSql.save(manufacturer);
  }

  @Override
  public void delete(UUID id) {
    repositoryPostgreSql.deleteById(id);
  }
}
