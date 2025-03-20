package com.highrock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Account
 * @description: 账号表
 * @author: Jony Z
 * @datetime: 2025/03/11 15:12
 * @version: 1.0
 */
@Data
@TableName("tb_account")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private Integer age;
    private Date birthday;

}
