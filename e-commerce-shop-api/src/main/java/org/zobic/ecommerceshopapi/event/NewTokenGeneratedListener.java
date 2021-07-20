package org.zobic.ecommerceshopapi.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.zobic.ecommerceshopapi.model.User;
import org.zobic.ecommerceshopapi.service.UserService;
import org.zobic.ecommerceshopapi.util.Utility;

import java.util.Locale;

@Component
public class NewTokenGeneratedListener extends GenericMailListener implements ApplicationListener<NewTokenGeneratedEvent> {

  @Override
  @Autowired
  protected void autowireAttributes(JavaMailSender javaMailSender, MessageSource messages, UserService userService) {
    super.autowireAttributes(javaMailSender, messages, userService);
  }

  @Override
  public void onApplicationEvent(NewTokenGeneratedEvent event) {
    User user = event.getUser();
    String recipient = user.getEmail();
    String subject = "New Confirmation Link";
    String confirmationUrl =  "/api/registrationConfirmation?token=" + event.getVerificationToken().getId().toString();
    String message = "Username: " + user.getUsername() + "\n\n";
    message += messages.getMessage("message.regReSendSuccLink", null, Locale.ENGLISH);
    message += "\r\n" + this.domain + confirmationUrl;
    mailSender.send(Utility.generateMailMessage(subject, message, recipient, this.sender));
  }
}
