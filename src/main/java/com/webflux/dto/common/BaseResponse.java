package com.webflux.dto.common;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private String requestId;
    private String requestType;
    private String requestDateTime;
    private BaseResult result;
    private T data;


    public static <T> BaseResponse<T> buildResponse(@Nullable BaseRequest<?> req, T body, String responseCode, String description) {
        var resp = new BaseResponse<T>();
        if (req != null) {
            resp.setRequestId(req.getRequestId());
            resp.setRequestDateTime(req.getRequestDateTime());
            resp.setRequestType(req.getRequestType());
        }
        resp.setData(body);
        resp.setResult(
                BaseResult.builder().code(responseCode).message(description).build()
        );
        return resp;
    }
}

