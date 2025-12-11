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
 * 卖家信息
 * </p>
 *
 * @author lggyx
 * @since 2025-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("seller")
@Schema(name="Seller对象", description="卖家信息")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "创建时间")
    private LocalDateTime addTime;

    @Schema(name = "卖家用户名")
    private String sellerName;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "真实姓名")
    private String realName;

    @Schema(name = "性别")
    private String gender;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "头像URL")
    private String photo;


}
