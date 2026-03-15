package io.sapo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomBusinessException extends RuntimeException {
    private int errorCode;
    private String message;

    public CustomBusinessException(String message) {
        this.errorCode = 400;
        this.message = message;
    }
}
