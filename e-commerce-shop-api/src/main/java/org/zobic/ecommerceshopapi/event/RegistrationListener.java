package org.zobic.ecommerceshopapi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.model.VerificationToken;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.util.Utility;

import java.util.Locale;

@Component
public class RegistrationListener extends GenericMailListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  @Autowired
  @Override
  protected void autowireAttributes(JavaMailSender javaMailSender, MessageSource messages, UserService userService) {
    super.autowireAttributes(javaMailSender, messages, userService);
  }

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    this.confirmRegistration(event);
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) {
    User user = event.getUser();
    VerificationToken token = userService.createVerificationToken(user);

    String recipient = user.getEmail();
    String subject = "Confirm Registration";
    String confirmationUrl = event.getUrl() + "/api/registrationConfirmation?token=" + token.getId().toString();
    String message = "Username: " + user.getUsername() + "\n\n";
    message += messages.getMessage("message.regSuccLink", null, Locale.ENGLISH);
    message += "\r\n" + this.domain + confirmationUrl;

    mailSender.send(Utility.generateMailMessage(subject, message, recipient, this.sender));
  }
}
