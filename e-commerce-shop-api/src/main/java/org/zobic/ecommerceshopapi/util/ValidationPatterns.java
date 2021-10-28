package org.zobic.ecommerceshopapi.util;

public class ValidationPatterns {

  public static final String EMAIL_PATTERN =
    "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
      + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
}
