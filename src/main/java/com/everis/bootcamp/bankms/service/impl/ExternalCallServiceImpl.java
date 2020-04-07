package com.everis.bootcamp.bankms.service.impl;

import com.everis.bootcamp.bankms.dto.MessageDto;
import com.everis.bootcamp.bankms.service.ExternalCallService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExternalCallServiceImpl implements ExternalCallService {

  @Override
  public Mono<MessageDto> depositRet(String numAccount, double money) {
    String url = "http://localhost:8010/bankprod/transaction/" + numAccount;
    return WebClient.create()
        .post()
        .uri(url)
        .bodyValue(money)
        .retrieve()
        .bodyToMono(MessageDto.class);
  }

  @Override
  public Mono<MessageDto> payCreditDebt(String numAccount, String creditNumber) {
    String url = "http://localhost:8010/bankprod/payCreditDebt/" + numAccount + "/" + creditNumber;
    return WebClient.create()
        .post()
        .uri(url)
        .retrieve()
        .bodyToMono(MessageDto.class);
  }

  @Override
  public Mono<MessageDto> bankTransaction(String numAccountOrigin, String numAccountDestination,
      double money) {
    String url = "http://localhost:8010/bankprod/bankProductTransaction/" + numAccountOrigin + "/"
        + numAccountDestination;
    return WebClient.create()
        .post()
        .uri(url)
        .bodyValue(money)
        .retrieve()
        .bodyToMono(MessageDto.class);
  }

  @Override
  public Mono<String> getProductBankId(String numAccount) {
    String url = "http://localhost:8010/bankprod/getBankId/" + numAccount;
    return WebClient.create()
        .get()
        .uri(url)
        .retrieve()
        .bodyToMono(String.class);
  }

  @Override
  public Mono<MessageDto> chargeComission(String numAccount, double comission) {
    String url = "http://localhost:8010/bankprod/chargeExtComission/" + numAccount;
    return WebClient.create()
        .post()
        .uri(url)
        .bodyValue(comission)
        .retrieve()
        .bodyToMono(MessageDto.class);
  }

}
