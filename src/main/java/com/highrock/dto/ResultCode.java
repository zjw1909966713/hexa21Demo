package com.highrock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * @decription:结果返回枚举类型
 * @author: Jony Z
 * @date: 2023/4/15 16:16
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200,"success"),
    FAILURE(500,"failure"),
    UNAUTHORIZED(401, "Not logged in yet or token has expired"),
    FORBIDDEN(403, "no permission"),
    PARAMETER_DATA_ERROR(501,"Parameter conversion exception");
    ;


    private Integer code;
    private String message;

}
