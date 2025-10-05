package com.webflux.constant;


import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResponseCodeMapper {
    SUCCESSFULLY("000", "Success", "0000"),
    INVALID_INPUT("", "Invalid input", "0310"),
    SYSTEM_MALFUNCTION("909", "System malfunction", "9600"),
    ISSUER_TIME_OUT("911", "Response received too late (timeout)", "6800"),
    ERROR("", "Error", "0600"),
    DATA_NOT_FOUND("", "Data not found", "1400"),
    DUPLICATE_DATA("", "Duplicate data", "2600"),
    DATA_T24_NOT_FOUND("", "Data T24 not found", "1402"),
    INVALID_TRANSACTION("", "Invalid transaction", "1200");

    ResponseCodeMapper(String coreBankCode, String description, String compositCode) {
        this.coreBankCode = coreBankCode;
        this.description = description;
        this.compositCode = compositCode;
    }

    private final String coreBankCode;
    private final String description;
    private final String compositCode;

    public static ResponseCodeMapper get(String coreBankCode) {
        return Arrays.stream(values())
                .filter(iso8583 -> iso8583.coreBankCode.equals(coreBankCode))
                .findFirst().orElse(ERROR);
    }

}
