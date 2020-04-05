package com.everis.bankms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankMaxTransDTO {
    private HashMap<String, Integer> productMaxTrans;
}