package com.highrock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;

/***
 * @decription: 通用结果返回类型
 * @author: Jony Z
 * @date: 2023/4/15 16:15
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CommonResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //状态码(只有200为成功)
    private Integer code;

    //提示信息
    private String message;

    //返回的数据
    private T data;


    public static <T>  CommonResponse<T> success() {
        return new CommonResponse<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);

    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> CommonResponse<T> failure(String message) {
        return new CommonResponse<>(ResultCode.FAILURE.getCode(), message, null);
    }

    public static <T> CommonResponse<T> failure(ResultCode resultCode) {
        return new CommonResponse<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

}
