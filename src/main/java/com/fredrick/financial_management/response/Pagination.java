package com.fredrick.financial_management.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    private int page;
    private int size;
    private int totalPage;
    private int totalSize;
}
