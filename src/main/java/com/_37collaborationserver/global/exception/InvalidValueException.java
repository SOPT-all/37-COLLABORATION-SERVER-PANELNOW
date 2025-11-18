package com._37collaborationserver.global.exception;

import com._37collaborationserver.global.exception.code.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
