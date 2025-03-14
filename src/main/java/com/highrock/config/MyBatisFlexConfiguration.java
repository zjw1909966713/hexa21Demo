package com.highrock.config;

import com.mybatisflex.core.audit.AuditManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MyBatisFlexConfiguration
 * @description: mybatis-flex配置类
 * @author: Jony Z
 * @datetime: 2025/03/11 16:13
 * @version: 1.0
 */
@Configuration
@Slf4j
public class MyBatisFlexConfiguration {

    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                log.info("{},{}ms", auditMessage.getFullSql()
                        , auditMessage.getElapsedTime())
        );
    }
}
