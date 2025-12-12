package com.lggyx.enumeration;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoleCode {
    USER_ROLE_CODE("user___", 1),
    SELLER_ROLE_CODE("seller_", 2),
    ADMIN_ROLE_CODE("admin__", 3);
    private final String roleStr;
    private final int roleCode;

    RoleCode(String roleStr, int roleCode) {
        this.roleStr = roleStr;
        this.roleCode = roleCode;
    }


    // 更安全：通过账号前缀查找
    public static RoleCode fromAccount(String account) {
        return Arrays.stream(values())
                .filter(role -> account.startsWith(role.getRoleStr()))
                .findFirst()
                .orElse(USER_ROLE_CODE); // 提供默认值
    }
}
