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
 * 旧物评论
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item_comments")
@Schema(name="ItemComments对象", description="旧物评论")
public class ItemComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "创建时间")
    private LocalDateTime addTime;

    @Schema(name = "旧物ID")
    private Long itemId;

    @Schema(name = "用户ID")
    private Long userId;

    @Schema(name = "评论内容")
    private String content;

    @Schema(name = "回复内容")
    private String reply;


}
