package com.webflux.dto.common;

import lombok.Data;

@Data
public class BaseRequest<T> {
    private String requestId;
    private String requestType;
    private String requestDateTime;
    private String logId;

    private T data;

}
