package com.webflux.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.webflux.constant.ResponseCodeMapper;
import com.webflux.constant.Result;
import com.webflux.constant.Status;
import com.webflux.dto.common.BaseRequest;
import com.webflux.dto.common.BaseResponse;
import com.webflux.handler.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtils {
    final ObjectMapper customObjectMapper;
    final Gson gson;
    final Validator validator;


    public static String generateLogId() {
        return java.util.UUID.randomUUID().toString();
    }

    public void writeLogRequest(String lmid, String functionName, Object request) {
        log.info("{}: Status={} Function={}", lmid, Status.INCOMING, StringUtils.capitalize(functionName));
        log.info("{}: Request={}", lmid, gson.toJson(request));
    }

    public <T> void validateRequest(BaseRequest<T> dto, Class<T> dataType, String functionName) throws ValidationException {
        long startTime = System.currentTimeMillis();
        try {
            log.info("{}: {} toValidator", dto.getLogId(), functionName);

            var messageErrorDto = validator.validate(dto).stream()
                    .map(ConstraintViolation::getMessage);

            var data = customObjectMapper.convertValue(dto.getData(), dataType);
            var messageErrorData = validator.validate(data).stream()
                    .map(ConstraintViolation::getMessage);

            var messageErrorMerge = Stream.concat(messageErrorDto, messageErrorData).sorted().toList();
            if (!CollectionUtils.isEmpty(messageErrorMerge)) {
                throw new ValidationException(String.join("; ", messageErrorMerge), gson.toJson(dto));
            }
        } finally {
            log.info("{}: {} frValidator ExecuteTime={}ms", dto.getLogId(), functionName, System.currentTimeMillis() - startTime);
        }
    }

    public <T> void writeLogResponse(String lmid, BaseResponse<T> response, String functionName, long timeStart) {
        var responseCode = response.getResult().getCode();
        log.info("{}: Response={}", lmid, gson.toJson(response));
        log.info("{}: Status={} Function={} TotalExecuteTime={}ms Result={}"
                , lmid, Status.COMPLETED
                , StringUtils.capitalize(functionName)
                , System.currentTimeMillis() - timeStart
                , ResponseCodeMapper.SUCCESSFULLY.getCompositCode().equals(responseCode) ?
                        Result.SUCCESSFUL : getResponseCodeFailure(responseCode));
    }

    private static Result getResponseCodeFailure(String responseCode) {
        return responseCode.equals("6800") ? Result.TIMED_OUT : Result.FAILED;
    }


}
