package com.lggyx.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    @Schema(description = "总记录数")
    private long total; //总记录数
    @Schema(description = "当前页码")
    private long page;
    @Schema(description = "每页记录数")
    private long size;
    @Schema(description = "当前页数据")
    private List records; //当前页数据集合


}