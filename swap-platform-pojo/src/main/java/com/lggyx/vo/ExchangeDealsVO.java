package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExchangeDealsVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime addTime;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "目标旧物名称")
    private String targetItemName;

    @Schema(description = "目标旧物图片")
    private String targetItemImage;

    @Schema(description = "置换物名称")
    private String offerItemName;

    @Schema(description = "置换物图片")
    private String offerItemImage;

    @Schema(description = "置换物详情")
    private String offerDetail;

    @Schema(description = "是否审核")
    private String approved;

    @Schema(description = "审核回复")
    private String approvalReply;
}
