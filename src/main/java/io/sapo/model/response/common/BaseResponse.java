package io.sapo.model.response.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class BaseResponse<T> {
    @Builder.Default
    private boolean success = true;
    private String message;

    private int code;
    private T data;
    private Object page;

    public <E> BaseResponse<T> withPageData(Page<E> page) {
        this.page = PageResponse.builder()
                .index(page.getTotalPages() == 0
                        ? 0 : page.getNumber() + 1)
                .size(page.getSize())
                .elements(page.getTotalElements())
                .pages(page.getTotalPages())
                .build();
        return this;
    }
}