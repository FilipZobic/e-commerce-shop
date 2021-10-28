package org.zobic.ecommerceshopapi.session;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
// koristi builder
@Data
public class Session implements Serializable {

  private String id; // final

  private UUID userId; // final

  private UserDetails userDetails;

  private Date validUntil; // add validation can't be more then 30 minutes everytime we get it refresh currentTime + 30 minutes if they try and access it it wont allow
}
