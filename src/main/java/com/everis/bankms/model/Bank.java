package com.everis.bankms.model;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BANK")
@EqualsAndHashCode(callSuper = false)
public class Bank {
    @Id
    private String id;
    @NotBlank(message = "'numId' is required")
    private String numId;
    @NotBlank(message = "'name' is required")
    private String name;
    private Set<String> clientProfiles;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;
}