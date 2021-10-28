package org.zobic.ecommerceshopapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zobic.ecommerceshopapi.model.CartItem;
import org.zobic.ecommerceshopapi.model.Laptop;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryResultCartItem {

  private Laptop laptop;

  private CartItem cartItem;
}
