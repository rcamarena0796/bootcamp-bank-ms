package com.everis.bankms.service.impl;


import java.util.Date;

import com.everis.bankms.dao.BankRepository;
import com.everis.bankms.model.Bank;
import com.everis.bankms.service.BankService;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Flux;

@Service
public class BankServiceImpl implements BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);

    @Autowired
    private BankRepository bankRepo;


    @Override
    public Mono<Bank> findByName(String name) {
        return bankRepo.findByName(name);
    }

    @Override
    public Flux<Bank> findAll() {
        return bankRepo.findAll();
    }

    @Override
    public Mono<Bank> findById(String id) {
        return bankRepo.findByNumId(id);
    }


    @Override
    public Mono<Bank> save(Bank cl) {
        try {
            if (cl.getJoinDate() == null) {
                cl.setJoinDate(new Date());
            } else {
                cl.setJoinDate(cl.getJoinDate());
            }
            return bankRepo.save(cl);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Bank> update(Bank cl, String id) {
        try {
            return bankRepo.findById(id)
                    .flatMap(dbClient -> {

                        //JoinDate
                        if (cl.getJoinDate() != null) {
                            dbClient.setJoinDate(cl.getJoinDate());
                        }

                        //name
                        if (cl.getName() != null) {
                            dbClient.setName(cl.getName());
                        }

                        //name
                        if (cl.getNumId() != null) {
                            dbClient.setNumId(cl.getNumId());
                        }

                        return bankRepo.save(dbClient);

                    }).switchIfEmpty(Mono.error(new Exception ("No se pudo encontrar el banco que se quiere actualizar")));
        } catch (Exception e) {
            return Mono.error(e);
        }

    }

    @Override
    public Mono<Void> delete(String id) {
        try {
            return bankRepo.findById(id).flatMap(cl -> {
                return bankRepo.delete(cl);
            });
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Boolean> existsByNumId(String numId) {
        return bankRepo.existsByNumId(numId);
    }

    @Override
    public Mono<String> getNumId(String numId) {
        try {
            Mono<Bank> bank = bankRepo.findByNumId(numId).switchIfEmpty(Mono.justOrEmpty(new Bank()));
            Mono<String> ret = bank.map(cl -> {
                logger.info("ENCONTRE ESTO :" + cl.getName());
                if (cl.getNumId() != null) {
                    return cl.getNumId();
                } else return "-1";
            });
            return ret;
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}