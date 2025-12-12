package com.lggyx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class ExchangeDeals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
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
