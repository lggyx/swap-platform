package com.lggyx.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 置换交易
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exchange_deals")
@Schema(name="ExchangeDeals对象", description="置换交易")
public class ExchangeDealsDTO {

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



}
