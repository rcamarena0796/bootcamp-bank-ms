package com.everis.bootcamp.bankms.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BankMaxTransDto {

  private HashMap<String, Integer> productMaxTrans;
}