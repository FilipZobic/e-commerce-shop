package org.zobic.ecommerceshopapi.dto.paramaters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPageParams extends BasePageParams {

  private String username = "";

  private Boolean deleted = null;

  private Boolean enabled = null;

  private String role = "";
}
