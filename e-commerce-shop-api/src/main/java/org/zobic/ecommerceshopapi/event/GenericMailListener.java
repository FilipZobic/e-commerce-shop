package org.zobic.ecommerceshopapi.event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.zobic.ecommerceshopapi.service.UserService;


public abstract class GenericMailListener {

  protected JavaMailSender mailSender;

  protected MessageSource messages;

  protected UserService userService;

  @Value("${mail.automatic.address}")
  protected String sender;

  @Value("${link.value}")
  protected String domain;

  protected void autowireAttributes(JavaMailSender javaMailSender, MessageSource messages, UserService userService) {
    this.mailSender = javaMailSender;
    this.messages = messages;
    this.userService = userService;
  }
}
