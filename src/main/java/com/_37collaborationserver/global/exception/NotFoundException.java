package com._37collaborationserver.global.exception;

import com._37collaborationserver.global.exception.code.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
