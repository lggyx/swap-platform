package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ItemDTO {

    @Schema(description = "旧物名称")
    private String itemName;

    @Schema(description = "旧物类型")
    private String itemCategory;

    @Schema(description = "旧物图片URL")
    private String itemImage;

    @Schema(description = "置换需求")
    private String exchangeRequest;

    @Schema(description = "旧物详情")
    private String itemDetail;

}
