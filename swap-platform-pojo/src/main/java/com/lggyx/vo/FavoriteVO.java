package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 用户收藏
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
public class FavoriteVO {

    @Schema(description = "旧物ID")
    private Long itemId;
    @Schema(description = "旧物类型")
    private String itemType;

    @Schema(description = "收藏名称")
    private String name;

    @Schema(description = "收藏图片URL")
    private String image;


}
