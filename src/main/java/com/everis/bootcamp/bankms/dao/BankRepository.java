package com.everis.bootcamp.bankms.dao;

import com.everis.bootcamp.bankms.model.Bank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BankRepository extends ReactiveMongoRepository<Bank, String> {

  public Mono<Bank> findByName(String nombre);

  public Mono<Bank> findByNumId(String numDoc);

  public Mono<Boolean> existsByNumId(String numId);
}
