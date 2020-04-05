package com.everis.bankms.service;

import com.everis.bankms.dto.BankMaxTransDTO;
import com.everis.bankms.dto.ClientProfilesDTO;
import com.everis.bankms.dto.MessageDTO;
import com.everis.bankms.model.Bank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankService {
    public Mono<Bank> findByName(String name);

    public Mono<Bank> findById(String id);

    public Flux<Bank> findAll();

    public Mono<Bank> update(Bank c, String id);

    public Mono<Void> delete(String id);

    public Mono<Bank> save(Bank cl);

    public Mono<Boolean> existsByNumId(String numDoc);

    public Mono<String> getNumId(String numDoc);

    public Mono<ClientProfilesDTO> getClientProfiles(String numId);

    public Mono<BankMaxTransDTO> getbankComission(String numId);

    public Mono<MessageDTO> otherBankDepositRet(String idBankOrigin, String numAccount, double money);

    public Mono<MessageDTO> otherBankPayCreditDebt(String idBankOrigin, String numAccount,String creditNumber);

    public Mono<MessageDTO> otherBankTransaction(String idBankOrigin, String numAccountOri, String numAccountDes, double money);
}
