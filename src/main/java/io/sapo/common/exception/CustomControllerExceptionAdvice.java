package io.sapo.common.exception;

import io.sapo.common.util.ResponseUtils;
import io.sapo.model.response.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(HttpServletRequest request, Exception e) {
        System.err.print(e.getStackTrace());
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return ResponseEntity.status(code)
                .body(ResponseUtils.error(code, e.getMessage()));
    }

    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomBusinessException(CustomBusinessException e) {
        return ResponseEntity.ok(ResponseUtils.error(e.getErrorCode(), e.getMessage()));
    }
}
