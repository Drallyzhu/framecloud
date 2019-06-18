package com.framecloud.common.base.exception;


public class PermissionDefinedException extends RuntimeException {

    public PermissionDefinedException() {
        super("权限不足，访问失败");
    }
}
