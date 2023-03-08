package com.fhyj.common.base.bean;

import com.fhyj.common.base.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/20 10:34
 */
@Getter
@Setter
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return fail(ResultEnum.FAIL.getCode(), message);
    }

    public static <T> Result<T> fail() {
        return fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage());
    }


}
