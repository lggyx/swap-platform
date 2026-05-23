package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StatisticsVO {
    @Schema(description = "用户总数")
    private Long totalUsers;
    @Schema(description = "卖家总数")
    private Long totalSellers;
    @Schema(description = "物品总数")
    private Long totalItems;
    @Schema(description = "置换交易总数")
    private Long totalDeals;
}
