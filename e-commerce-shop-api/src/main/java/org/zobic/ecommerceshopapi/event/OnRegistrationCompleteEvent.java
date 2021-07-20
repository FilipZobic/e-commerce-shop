package org.zobic.ecommerceshopapi.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.zobic.ecommerceshopapi.model.User;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
  private Locale locale;
  private User user;
  private String url;

  public OnRegistrationCompleteEvent(User user, Locale locale, String url) {
    super(user);

    this.user = user;
    this.locale = locale;
    this.url = url;
  }
}
