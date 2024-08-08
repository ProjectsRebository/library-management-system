package com.library.management.system.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto<T> {

    private Long totalNumberOfElements;
    private Long pageSize;
    private Long currentPage;
    private Long numberOfPages;
    private Long fromIndex;
    private Long toIndex;
    private List<T> values;

}
