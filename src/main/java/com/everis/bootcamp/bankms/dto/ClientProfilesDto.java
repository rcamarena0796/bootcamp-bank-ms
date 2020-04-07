package com.everis.bootcamp.bankms.dto;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientProfilesDto {

  private Set<String> clientProfiles;
}