package org.zobic.ecommerceshopapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zobic.ecommerceshopapi.dto.QueryResultCartItem;
import org.zobic.ecommerceshopapi.model.Laptop;

import java.util.List;
import java.util.UUID;

@Repository
public interface LaptopRepositoryPostgreSql extends JpaRepository <Laptop, UUID> {

  @Query("SELECT laptop FROM Laptop laptop WHERE " +
    "(:productName IS NULL OR UPPER(laptop.name) LIKE %:productName% )" +
    "AND (laptop.price >= :minPrice OR :minPrice IS NULL)" +
    "AND (laptop.price <= :maxPrice OR :maxPrice IS NULL)" +
    "AND (laptop.manufacturer.id = :manufacturerId OR :manufacturerId IS NULL)" +
    "AND (laptop.stock > 0 OR laptop.stock IS NULL OR :shouldShowOutOfStock = true)"
  )
  Page<Laptop> findAllPaging(Pageable pageable, String productName, Double minPrice, Double maxPrice, UUID manufacturerId,Boolean shouldShowOutOfStock);

  @Query("SELECT MAX(laptop.price) FROM Laptop laptop")
  Integer findMaxPrice();

  @Query("SELECT DISTINCT new org.zobic.ecommerceshopapi.dto.QueryResultCartItem(laptop, cart) FROM Laptop laptop " +
    "JOIN CartItem cart ON laptop.id = cart.id.laptopId " +
    "LEFT OUTER JOIN User user ON user.id = cart.id.userId WHERE user.id = :id")
    List<QueryResultCartItem> findAllByUserId(UUID id);
}
