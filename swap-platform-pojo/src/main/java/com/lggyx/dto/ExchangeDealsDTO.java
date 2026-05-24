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
public class ExchangeDealsDTO {

    @Schema(description = "目标旧物ID")
    private Long targetItemId;

    @Schema(description = "置换物名称")
    private String offerItemName;

    @Schema(description = "置换物图片")
    private String offerItemImage;

    @Schema(description = "置换物详情")
    private String offerDetail;

}
