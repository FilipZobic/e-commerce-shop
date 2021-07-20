package org.zobic.ecommerceshopapi.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.model.VerificationToken;

import java.util.Locale;

@Getter
@Setter
public class NewTokenGeneratedEvent extends ApplicationEvent {

  private Locale locale;
  private User user;
  private VerificationToken verificationToken;

  public NewTokenGeneratedEvent(Locale locale, User user, VerificationToken verificationToken) {
    super(user);
    this.locale = locale;
    this.user = user;
    this.verificationToken = verificationToken;
  }
}
