package org.zobic.ecommerceshopapi.repository;

import org.zobic.ecommerceshopapi.model.Manufacturer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManufacturerRepository {

  List<Manufacturer> getAll();

  Manufacturer save(Manufacturer manufacturer);

  void delete(UUID id);

  Optional<Manufacturer> findById(UUID id);
}
