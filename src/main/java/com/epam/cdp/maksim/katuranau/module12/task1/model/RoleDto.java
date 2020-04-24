package com.epam.cdp.maksim.katuranau.module12.task1.model;

public class RoleDto {

    private Long userId;
    private Integer roleId;

    public RoleDto(final Long userId, final Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(final Integer roleId) {
        this.roleId = roleId;
    }
}
