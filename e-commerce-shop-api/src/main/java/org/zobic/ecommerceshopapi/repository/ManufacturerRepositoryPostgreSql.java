package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Manufacturer;

import java.util.UUID;

@Repository
public interface ManufacturerRepositoryPostgreSql extends JpaRepository <Manufacturer, UUID> {
}
