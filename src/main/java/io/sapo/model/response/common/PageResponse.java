package io.sapo.model.response.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponse {
    private int index;
    private int size;
    private long elements;
    private int pages;
}