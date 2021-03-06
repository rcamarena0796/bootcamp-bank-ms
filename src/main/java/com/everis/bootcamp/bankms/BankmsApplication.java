package com.everis.bootcamp.bankms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BankmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankmsApplication.class, args);
  }
}
