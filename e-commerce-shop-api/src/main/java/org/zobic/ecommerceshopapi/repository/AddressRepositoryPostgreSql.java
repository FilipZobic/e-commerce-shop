package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.model.Address;

import java.util.UUID;

@Repository
public interface AddressRepositoryPostgreSql extends JpaRepository<Address, UUID> {
}
