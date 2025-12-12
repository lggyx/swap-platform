package com.lggyx.enumeration;

import lombok.Getter;

@Getter
public enum RoleCode {
    USER_ROLE_CODE("user_", 1),
    SELLER_ROLE_CODE("seller_", 2),
    ADMIN_ROLE_CODE("admin_", 3);
    private final String roleStr;
    private final int roleCode;

    RoleCode(String roleStr, int roleCode) {
        this.roleStr = roleStr;
        this.roleCode = roleCode;
    }

}
