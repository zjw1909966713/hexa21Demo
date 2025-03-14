package com.highrock.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson2.JSON;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Enumeration;

/***
 * @decription: 定义一个切面，统一处理web请求的日志
 * @author: Jony Z
 * @date: 2025-03-13 09:37
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * com.highrock.controller.*.*(..))")
    public void WebLog() {
    }


    @Before("WebLog()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("");
        // 接收到请求，记录请求内容
        log.info("*********************************************request start:{}*************************************************************", DateUtil.now());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        // 记录下请求内容
        log.info("URL : {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD : {}", request.getMethod());
        log.info("IP : {}", getIpAddr(request));
        if (ObjectUtil.isNotNull(userAgent)){
            log.info("BROWSER VERSION: {} {}", userAgent.getBrowser().toString(), userAgent.getVersion());
            log.info("SYSTEM OS : {}", userAgent.getOs().toString());
            log.info("PLATFORM : {}", userAgent.getPlatform());
        }else {
            log.info("BROWSER VERSION: null null");
            log.info("SYSTEM OS : null");
            log.info("PLATFORM : null");
        }
        log.info("Authorization : {}", request.getHeader("Authorization"));
        log.info("Fingerprint : {}", request.getHeader("Fingerprint"));
        log.info("CLASS_METHOD : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature
                ().getName());
        log.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));

        //获取所有参数方法一：
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            log.info("{}: {}", paraName, request.getParameter(paraName));
        }

    }


    @AfterReturning(pointcut = "WebLog()", returning = "retVal")
    public void doAfterReturning( Object retVal) {
        // 处理完请求，返回内容
        log.info("RESPONSE : {}", JSON.toJSON(retVal));
        log.info("*********************************************request end:{}***************************************************************", DateUtil.now());

        log.info("");
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }
        return ip;
    }


}
