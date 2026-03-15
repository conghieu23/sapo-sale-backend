package io.sapo.common.util;

import io.sapo.model.response.common.BaseResponse;
import org.springframework.data.domain.Page;

public class ResponseUtils {
    public static <T, E> BaseResponse<T> success(String message) {
        return build(true, 0, message, null);
    }

    public static <T, E> BaseResponse<T> success(String message, T data) {
        return build(true, 0, message, data);
    }

    public static <T, E> BaseResponse<T> success(String message, T data, Page<E> page) {
        return success(message, data)
                .withPageData(page);
    }

    public static <T, E> BaseResponse<T> error(int code, String message) {
        return build(false, code, message, null);
    }

    private static <T> BaseResponse<T> build(boolean success, int code, String message, T data) {
        return BaseResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
