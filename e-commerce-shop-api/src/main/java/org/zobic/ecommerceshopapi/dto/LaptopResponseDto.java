package org.zobic.ecommerceshopapi.dto;

import lombok.Builder;
import lombok.Data;
import org.zobic.ecommerceshopapi.model.CartItem;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class LaptopResponseDto {

  private UUID id;

  private String name;

  private String image;

  private ManufacturerDto manufacturer;

  private Double price;

  private Float diagonal;

  private String panelType;

  private Integer ram;

  private Integer stock;

  private boolean canDelete;

  private CartItem cartInfo;
}
