package com.everis.bootcamp.bankms.service;

import com.everis.bootcamp.bankms.dto.MessageDto;
import reactor.core.publisher.Mono;

public interface ExternalCallService {

  public Mono<MessageDto> depositRet(String numAccount, double money);


  public Mono<MessageDto> payCreditDebt(String numAccount, String creditNumber);

  public Mono<MessageDto> bankTransaction(String numAccountOrigin, String numAccountDestination,
      double money);

  public Mono<String> getProductBankId(String numAccount);

  public Mono<MessageDto> chargeComission(String numAccount, double comission);

}
