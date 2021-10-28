package org.zobic.ecommerceshopapi.dto.paramaters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePageParams {

  private Integer page;

  private Integer size;
}
