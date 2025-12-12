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
 * 旧物信息
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item_info")
@Schema(name="ItemInfo对象", description="旧物信息")
public class ItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime addTime;

    @Schema(description = "卖家名")
    private String sellerName;

    @Schema(description = "联系人姓名")
    private String contactName;

    @Schema(description = "联系人手机")
    private String contactPhone;

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

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "点踩数")
    private Integer dislikeCount;


}
