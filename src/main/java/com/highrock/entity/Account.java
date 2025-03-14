package com.highrock.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
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
@Table("tb_account")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;
    private String userName;
    private Integer age;
    private Date birthday;

}
