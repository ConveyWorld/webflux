package com.webflux.controller;

import com.webflux.constant.ResponseCodeMapper;
import com.webflux.dto.common.BaseRequest;
import com.webflux.dto.common.BaseResponse;
import com.webflux.handler.ValidationException;
import com.webflux.utils.CommonUtils;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

@Slf4j
public class CommonController {

    @Autowired
    protected CommonUtils commonUtils;

    protected <U, T, V> Mono<BaseResponse<V>> processRestApi(
            BaseRequest<T> request
            , Class<T> clazz
            , Callable<Mono<U>> serviceImpl
            , Function<U, BaseResponse<V>> toResponseDto
            , String functionName
            , long startTime
    ) {
        try {
            request.setLogId(CommonUtils.generateLogId());
            commonUtils.writeLogRequest(request.getLogId(), functionName, request);
            commonUtils.validateRequest(request, clazz, functionName);
            return serviceImpl.call()
                    .map(toResponseDto)
                    .onErrorResume(Exception.class, exception -> {
                        log.error("{}: {} GrpcWebFluxException={}", request.getLogId(), functionName, exception.getMessage(), exception);
                        return Mono.just(handleException(exception, request));
                    }).doOnSuccess(data -> commonUtils.writeLogResponse(request.getLogId(), data, functionName, startTime));
        } catch (Exception ex) {
            log.error("{}: {}  RestErrorMessage={}", request.getLogId(), functionName, ex.getMessage(), ex);
            return Mono.<BaseResponse<V>>just(handleException(ex, request))
                    .doOnSuccess(data -> commonUtils.writeLogResponse(request.getLogId(), data, functionName, startTime));
        }
    }

    protected <T, V> BaseResponse<V> handleException(Exception ex, BaseRequest<T> request) {
        if (ex instanceof ValidationException) {
            return BaseResponse.buildResponse(request, null, ResponseCodeMapper.INVALID_INPUT.getCompositCode(), ex.getMessage());
        }
        if (ex instanceof StatusRuntimeException statusEx && statusEx.getStatus().getCode() == Status.DEADLINE_EXCEEDED.getCode()) {
            return BaseResponse.buildResponse(request, null, ResponseCodeMapper.ISSUER_TIME_OUT.getCompositCode()
                    , "Call grpc to atomic timed out!");
        }
        if (ex instanceof TimeoutException) {
            return BaseResponse.buildResponse(request, null, ResponseCodeMapper.ISSUER_TIME_OUT.getCompositCode()
                    , "Call webclient to atomic timed out!");
        }
        var resMessage = ResponseCodeMapper.ERROR.getDescription() + ": " + ex.getMessage();
        return BaseResponse.buildResponse(request, null, ResponseCodeMapper.ERROR.getCompositCode(), resMessage);
    }

}
