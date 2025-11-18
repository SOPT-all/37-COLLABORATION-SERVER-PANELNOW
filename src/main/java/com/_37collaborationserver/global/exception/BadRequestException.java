package com._37collaborationserver.global.exception;

import com._37collaborationserver.global.exception.code.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}

