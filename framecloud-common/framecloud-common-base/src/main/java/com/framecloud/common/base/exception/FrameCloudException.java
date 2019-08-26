package com.framecloud.common.base.exception;

public class FrameCloudException extends RuntimeException {

    private Integer code;
    private String errorMessage;

    public FrameCloudException() {
    }

    public FrameCloudException(String message) {
        super(message);
    }

    public FrameCloudException(MessageExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
