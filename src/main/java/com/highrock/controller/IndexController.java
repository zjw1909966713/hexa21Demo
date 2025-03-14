package com.highrock.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.highrock.dto.CommonResponse;
import com.highrock.entity.Account;
import com.highrock.mapper.AccountMapper;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/***
 * @decription: index
 * @author: Jony Z
 * @date: 2023/4/15 16:29
 * @version: 1.0
 */
@Slf4j
@RestController
public class IndexController {

    @Value("${spring.application.name}")
    private String serverName;

    @Resource
    private AccountMapper accountMapper;

    @Resource(name = "rThreadExecutor")
    private ThreadPoolTaskExecutor rExcutor;


    @Resource(name = "vThreadExecutor")
    private Executor vExcutor;


    @GetMapping("/")
    public String index() {
        String res = "{\"code\":200,\"message\":\"" + serverName + " success\",\"data\":\"" + DateUtil.now() + "\"}";
        return res;
    }


    @GetMapping("/account")
    public String account() {
        log.info("Current thread:{}", Thread.currentThread().getName());
        QueryWrapper queryWrapper = QueryWrapper.create().select(Account::getUserName, Account::getAge, Account::getBirthday).where(Account::getAge).ge(500);
        List<Account> accountList1 = accountMapper.selectListByQuery(queryWrapper);
        return JSON.toJSONString(accountList1);
    }

    @GetMapping("/vthread")
    public String vthread() {
        // 模拟 100 并发请求
        List<CompletableFuture<String>> futures = IntStream.range(0, 10000)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "HelloWorld" + i;
                }, vExcutor)).toList();

        // 等待所有请求完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        String collect = futures.stream().map(stringCompletableFuture -> {
            try {
                return stringCompletableFuture.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(","));
        return collect;


    }

    @GetMapping("/rthread")
    public String rthread() {
        // 模拟 100 并发请求
        List<CompletableFuture<String>> futures = IntStream.range(0, 10000)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "HelloWorld" + i;
                }, rExcutor)).toList();

        // 等待所有请求完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        String collect = futures.stream().map(stringCompletableFuture -> {
            try {
                return stringCompletableFuture.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.joining(","));
        return collect;


    }


    @GetMapping("/thread")
    public String thread() {
        StringBuilder stringBuilder = new StringBuilder();
        List<FutureTask> futureTaskList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            Callable<String> task = new Callable() {
                @Override
                public String call() throws Exception {
                    log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 业务处理
                    return "HelloWorld" + finalI;
                }
            };
            FutureTask futureTask = new FutureTask(task);
            futureTaskList.add(futureTask);
            Thread thread = new Thread(futureTask);
            thread.start();
        }
        for (FutureTask futureTask : futureTaskList) {
            String s = null;
            try {
                s = (String) futureTask.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            stringBuilder.append(s).append(",");
        }


        return stringBuilder.toString();


    }


    @GetMapping("/test")
    public CompletableFuture<CommonResponse<String>> test() {
        log.info("Current thread1:{},isVirtual1:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
        return CompletableFuture.supplyAsync(() -> {
            log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return CommonResponse.success("HelloWorld");
        }, rExcutor);
    }

    @GetMapping("/test1")
    public CompletableFuture<CommonResponse<String>> test1() {
        log.info("Current thread1:{},isVirtual1:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
        return CompletableFuture.supplyAsync(() -> {
            log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return CommonResponse.success("HelloWorld");
        }, vExcutor);
    }

    @GetMapping("/test2")
    public CommonResponse<String> test2() {
        log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CommonResponse.success("HelloWorld");

    }

    @GetMapping("/test3")
    public CommonResponse<String> test3() {
        log.info("Current thread1:{},isVirtual1:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
        new Thread(()->{
            try {
                log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return CommonResponse.success("HelloWorld");

    }

    @GetMapping("/test4")
    public CompletableFuture<CommonResponse<String>> test4() {
        log.info("Current thread1:{},isVirtual1:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
        return CompletableFuture.supplyAsync(() -> {
            log.info("Current thread:{},isVirtual:{}", Thread.currentThread().getName(), Thread.currentThread().isVirtual());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return CommonResponse.success("HelloWorld");
        });
    }


}
