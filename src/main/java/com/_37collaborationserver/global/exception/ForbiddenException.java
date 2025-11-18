package com._37collaborationserver.global.exception;

import com._37collaborationserver.global.exception.code.ErrorCode;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

