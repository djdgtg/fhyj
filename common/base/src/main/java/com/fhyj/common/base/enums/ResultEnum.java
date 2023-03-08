package com.fhyj.common.base.enums;

import lombok.Getter;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/20 10:37
 */
@Getter
public enum ResultEnum {

    SUCCESS(200, "Success."),
    FAIL(400, "Fail!");
    private final Integer code;
    private final String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
