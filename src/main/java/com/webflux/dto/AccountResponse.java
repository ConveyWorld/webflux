package com.webflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String accountId;
    private String name;
    private Integer age;
    private String email;

    private String status;
}
