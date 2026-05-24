package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemInfoVO {
    @Schema(description = "主键")
    private Long id;
    @Schema(description = "旧物名称")
    private String itemName;
    @Schema(description = "旧物类型")
    private String itemCategory;
    @Schema(description = "旧物图片URL")
    private String itemImage;
    @Schema(description = "置换需求")
    private String exchangeRequest;
    @Schema(description = "点赞数")
    private Integer likeCount;
    @Schema(description = "创建时间")
    private LocalDateTime addTime;
    @Schema(description = "卖家名")
    private String sellerName;
    @Schema(description = "联系人姓名")
    private String contactName;
    @Schema(description = "联系人手机")
    private String contactPhone;
    @Schema(description = "旧物详情")
    private String itemDetail;
    @Schema(description = "点踩数")
    private Integer dislikeCount;
}
