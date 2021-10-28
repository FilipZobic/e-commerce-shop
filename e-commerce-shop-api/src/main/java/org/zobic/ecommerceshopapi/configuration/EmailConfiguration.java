package org.zobic.ecommerceshopapi.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailConfiguration {

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Bean
  public JavaMailSenderImpl mailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

//    javaMailSender.setProtocol("SMTP");
    javaMailSender.setHost(this.host);
    javaMailSender.setPort(this.port);
    javaMailSender.setUsername(this.username);
    javaMailSender.setPassword(this.password);

    return javaMailSender;
  }
}
