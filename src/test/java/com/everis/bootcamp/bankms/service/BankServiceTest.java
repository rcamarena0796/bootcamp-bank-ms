package com.everis.bootcamp.bankms.service;

import static org.mockito.Mockito.when;

import com.everis.bootcamp.bankms.dao.BankRepository;
import com.everis.bootcamp.bankms.dto.BankMaxTransDto;
import com.everis.bootcamp.bankms.dto.ClientProfilesDto;
import com.everis.bootcamp.bankms.dto.MessageDto;
import com.everis.bootcamp.bankms.model.Bank;
import com.everis.bootcamp.bankms.service.impl.BankServiceImpl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(SpringExtension.class)
class BankServiceTest {

  @TestConfiguration
  static class BankServiceTestContextConfiguration {

    @Bean
    public BankService bankService() {
      return new BankServiceImpl();
    }
  }

  @Autowired
  private BankService bankService;

  @MockBean
  private BankRepository bankRepository;

  @MockBean
  private ExternalCallService externalCall;

  @Mock
  private Bank expectedBank1;

  @Mock
  private Bank expectedBank2;

  @Mock
  private Bank expectedBank3;

  @Mock
  private ClientProfilesDto profiles;

  @Mock
  private BankMaxTransDto maxTrans;

  @Mock
  private MessageDto message;

  @BeforeEach
  void setUp() {
    HashMap<String, Integer> hm = new HashMap<String, Integer>();
    hm.put("1", 1);
    hm.put("2", 2);
    hm.put("3", 3);

    expectedBank1 = Bank.builder().id("1").numId("1").name("bcp")
        .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
        .productMaxTrans(hm)
        .depRetComission(2).transactionComission(3).creditPayComission(4).build();

    expectedBank2 = Bank.builder().id("2").numId("2").name("bbvA")
        .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
        .productMaxTrans(hm)
        .depRetComission(2).transactionComission(3).creditPayComission(4).build();

    expectedBank3 = Bank.builder().id("3").numId("3").name("scotia")
        .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
        .productMaxTrans(hm)
        .depRetComission(2).transactionComission(3).creditPayComission(4).build();

    maxTrans = BankMaxTransDto.builder().productMaxTrans(expectedBank1.getProductMaxTrans()).build();

    message = MessageDto.builder().code("1").message("success").build();

    profiles = ClientProfilesDto.builder().clientProfiles(expectedBank1.getClientProfiles()).build();
  }


  @Test
  public void whenValidId_thenShouldBeFound() {
    when(bankRepository.findByNumId(expectedBank1.getId()))
        .thenReturn(Mono.just(expectedBank1));

    Mono<Bank> found = bankService.findById(expectedBank1.getId());

    assertResults(found, expectedBank1);
  }

  @Test
  public void findAll() {
    when(bankRepository.findAll())
        .thenReturn(Flux.just(expectedBank1, expectedBank2, expectedBank3));

    Flux<Bank> found = bankService.findAll();

    assertResults(found, expectedBank1, expectedBank2, expectedBank3);
  }

  @Test
  public void save() {
    when(bankRepository.save(expectedBank1))
        .thenReturn(Mono.just(expectedBank1));

    Mono<Bank> saved = bankService.save(expectedBank1);

    assertResults(saved, expectedBank1);
  }

  @Test
  public void update() {
    when(bankRepository.save(expectedBank1))
        .thenReturn(Mono.just(expectedBank1));
    when(bankRepository.findById(expectedBank1.getId())).thenReturn(Mono.just(expectedBank1));

    Mono<Bank> updated = bankService.update(expectedBank1, expectedBank1.getId());
    assertResults(updated, expectedBank1);
  }


  @Test
  public void delete() {
    when(bankRepository.findById(expectedBank1.getId()))
        .thenReturn(Mono.just(expectedBank1));
    when(bankRepository.delete(expectedBank1))
        .thenReturn(Mono.empty());

    Mono<String> deleted = bankService.delete(expectedBank1.getId());

    StepVerifier
        .create(deleted)
        .expectNext(expectedBank1.getId())
        .verifyComplete();
  }


  @Test
  public void whenValidId_thenExistsBynumIdShouldBeTrue() {
    when(bankRepository.existsByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(true));

    Mono<Boolean> found = bankService.existsByNumId(expectedBank1.getNumId());

    StepVerifier
        .create(found)
        .expectNext(true)
        .verifyComplete();
  }


  @Test
  public void whennotValidId_thenExistsBynumIdShouldBeFalse() {
    String id = "-1";
    when(bankRepository.existsByNumId(id))
        .thenReturn(Mono.just(false));

    Mono<Boolean> found = bankService.existsByNumId(id);

    StepVerifier
        .create(found)
        .expectNext(false)
        .verifyComplete();
  }


  @Test
  public void whenValidId_thenGetNumIdShouldReturn() {
    when(bankRepository.findByNumId(expectedBank1.getId()))
        .thenReturn(Mono.just(expectedBank1));

    Mono<String> found = bankService.getNumId(expectedBank1.getNumId());

    StepVerifier
        .create(found)
        .expectNext(expectedBank1.getNumId())
        .verifyComplete();
  }

  @Test
  public void whenNotValidId_thenGetNumIdShouldNotReturn() {
    String id = "-1";
    when(bankRepository.findByNumId(id))
        .thenReturn(Mono.just(new Bank()));

    Mono<String> found = bankService.getNumId(id);

    StepVerifier
        .create(found)
        .expectNext(id)
        .verifyComplete();
  }

  @Test
  public void whenValidId_thenGetClientProfilesShouldReturn() {
    when(bankRepository.findByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(expectedBank1));

    Mono<ClientProfilesDto> found = bankService.getClientProfiles(expectedBank1.getNumId());

    StepVerifier
        .create(found)
        .expectNext(profiles)
        .verifyComplete();
  }

  @Test
  public void whenValidId_thenGetBankMaxTransShouldReturn() {
    when(bankRepository.findByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(expectedBank1));

    Mono<BankMaxTransDto> found = bankService.getBankMaxTrans(expectedBank1.getNumId());

    StepVerifier
        .create(found)
        .expectNext(maxTrans)
        .verifyComplete();
  }

  @Test
  public void otherBankDepositRet() {
    String numAccount="111";
    double money =1;
    when(bankRepository.findByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(expectedBank1));
    when(externalCall.getProductBankId(numAccount)).thenReturn(Mono.just(expectedBank2.getNumId()));
    when(externalCall.depositRet(numAccount,money)).thenReturn(Mono.just(message));
    when(externalCall.chargeComission(numAccount,expectedBank1.getDepRetComission())).thenReturn(Mono.just(message));

    Mono<MessageDto> found = bankService.otherBankDepositRet(expectedBank1.getNumId(),numAccount,money);

    StepVerifier
        .create(found)
        .expectNext(message)
        .verifyComplete();
  }

  @Test
  public void otherBankPayCreditDebt() {
    String numAccount="111";
    String creditNumber="111";
    when(bankRepository.findByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(expectedBank1));
    when(externalCall.getProductBankId(numAccount)).thenReturn(Mono.just(expectedBank2.getNumId()));
    when(externalCall.payCreditDebt(numAccount,creditNumber)).thenReturn(Mono.just(message));
    when(externalCall.chargeComission(numAccount,expectedBank1.getCreditPayComission())).thenReturn(Mono.just(message));

    Mono<MessageDto> found = bankService.otherBankPayCreditDebt(expectedBank1.getNumId(),numAccount,creditNumber);

    StepVerifier
        .create(found)
        .expectNext(message)
        .verifyComplete();
  }

  @Test
  public void otherBankTransaction() {
    String numAccount1="111";
    String numAccount2="111";
    double money = 1;
    when(bankRepository.findByNumId(expectedBank1.getNumId()))
        .thenReturn(Mono.just(expectedBank1));
    when(externalCall.getProductBankId(numAccount1)).thenReturn(Mono.just(expectedBank2.getNumId()));
    when(externalCall.bankTransaction(numAccount1,numAccount2,money)).thenReturn(Mono.just(message));
    when(externalCall.chargeComission(numAccount1,expectedBank1.getTransactionComission())).thenReturn(Mono.just(message));

    Mono<MessageDto> found = bankService.otherBankTransaction(expectedBank1.getNumId(),numAccount1,numAccount2,money);

    StepVerifier
        .create(found)
        .expectNext(message)
        .verifyComplete();
  }

  private void assertResults(Publisher<Bank> publisher, Bank... expected) {
    StepVerifier
        .create(publisher)
        .expectNext(expected)
        .verifyComplete();
  }

}