package com.tim.chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@ComponentScan("com.tim")
@RestController
public class WebSocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebSocketApplication.class, args);
  }

  @Value("")
  private String applicationName;

  @RequestMapping("/")
  public String test(){
    return applicationName;
  }

}
