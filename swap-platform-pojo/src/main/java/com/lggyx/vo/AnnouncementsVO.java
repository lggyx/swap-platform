package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 平台公告
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
public class AnnouncementsVO {


    @Schema(description = "主键")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime addTime;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "简介")
    private String introduction;

    @Schema(description = "公告图片URL")
    private String image;

    @Schema(description = "公告内容")
    private String content;


}
