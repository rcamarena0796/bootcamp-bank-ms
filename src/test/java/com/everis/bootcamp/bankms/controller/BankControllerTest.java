package com.everis.bootcamp.bankms.controller;

import static org.mockito.Mockito.when;


import com.everis.bootcamp.bankms.dto.BankMaxTransDto;
import com.everis.bootcamp.bankms.dto.ClientProfilesDto;
import com.everis.bootcamp.bankms.model.Bank;
import com.everis.bootcamp.bankms.service.BankService;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BankController.class)
@Import(BankService.class)
public class BankControllerTest {

  @Mock
  private List<Bank> expectedBanks;

  @Mock
  private Bank expectedBank;

  @Mock
  private ClientProfilesDto profiles;

  @Mock
  private BankMaxTransDto maxTrans;


  @BeforeEach
  void setUp() {
    HashMap<String, Integer> hm = new HashMap<String, Integer>();
    hm.put("1", 1);
    hm.put("2", 2);
    hm.put("3", 3);

    profiles = ClientProfilesDto.builder().clientProfiles(expectedBank.getClientProfiles()).build();

    expectedBank = Bank.builder().id("1").numId("1").name("BCP")
        .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
        .productMaxTrans(hm)
        .depRetComission(2).transactionComission(3).creditPayComission(4).build();

    maxTrans = BankMaxTransDto.builder().productMaxTrans(expectedBank.getProductMaxTrans()).build();

    expectedBanks = Arrays.asList(
        Bank.builder().id("2").numId("2").name("bbvA")
            .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
            .productMaxTrans(hm)
            .depRetComission(2).transactionComission(3).creditPayComission(4).build(),
        Bank.builder().id("3").numId("3").name("ripley")
            .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
            .productMaxTrans(hm)
            .depRetComission(2).transactionComission(3).creditPayComission(4).build(),
        Bank.builder().id("4").numId("4").name("saga")
            .clientProfiles(Stream.of("1", "2").collect(Collectors.toCollection(HashSet::new)))
            .productMaxTrans(hm)
            .depRetComission(2).transactionComission(3).creditPayComission(4).build()
    );
  }

  @MockBean
  protected BankService service;

  @Autowired
  private WebTestClient webClient;

  private static Bank bankTest;

  @BeforeAll
  public static void setup() {
    bankTest = new Bank();
    bankTest.setName("BCP");
  }

  @Test
  public void test_controller_hola_mundo() {
    webClient.get()
        .uri("/bank/test")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Bank.class)
        .isEqualTo(bankTest);
  }

  @Test
  void getAllBanks() {
    when(service.findAll()).thenReturn(Flux.fromIterable(expectedBanks));

    webClient.get().uri("/bank/findAll")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Bank.class)
        .isEqualTo(expectedBanks);
  }

  @Test
  void getBankById_whenExists_returnCorrect() {
    when(service.findById(expectedBank.getId())).thenReturn(Mono.just(expectedBank));

    webClient.get()
        .uri("/bank/find/{id}", expectedBank.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Bank.class)
        .isEqualTo(expectedBank);
  }

  @Test
  void getBankById_whenNotExist_returnNotFound() {
    String id = "-1";
    when(service.findById(id)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("/bank/findById/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void getExistBankByNumId_whenExist_returnTrue() {
    when(service.existsByNumId(expectedBank.getNumId())).thenReturn(Mono.just(true));

    webClient.get()
        .uri("/bank/exist/{numId}", expectedBank.getNumId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Boolean.class)
        .isEqualTo(true);
  }


  @Test
  void getExistBankByNumId_whenNotExist_returnFalse() {
    String id = "-1";
    when(service.existsByNumId(id)).thenReturn(Mono.just(false));

    webClient.get()
        .uri("/bank/exist/{numId}", id)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Boolean.class)
        .isEqualTo(false);
  }


  @Test
  void getBankProfilesByNumId_whenExist_returnFound() {
    when(service.getClientProfiles(expectedBank.getNumId()))
        .thenReturn(Mono.just(profiles));

    webClient.get()
        .uri("/bank/bankProfiles/{numId}", expectedBank.getNumId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(ClientProfilesDto.class)
        .isEqualTo(profiles);
  }


  @Test
  void getBankProfilesByNumId_whenNotExist_returnError() {
    String id = "-1";
    when(service.existsByNumId(id)).thenReturn(Mono.error(new NotFoundException()));

    webClient.get()
        .uri("bank/bankProfiles/{numId}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

}
