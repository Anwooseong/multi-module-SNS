package com.social.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
@Getter
public class SliceResponse<T> {

    private List<T> data;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;

    public static <T> SliceResponse<T> of(Slice<T> result) {
        return SliceResponse.<T>builder()
                .data(result.getContent())
                .currentPage(result.getNumber())
                .pageSize(result.getSize())
                .hasNext(result.hasNext())
                .build();
    }
}
