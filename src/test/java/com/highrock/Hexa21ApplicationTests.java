package com.highrock;

import com.alibaba.fastjson2.JSON;
import com.highrock.entity.Account;
import com.highrock.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Hexa21ApplicationTests {

	@Resource
	private AccountMapper accountMapper;

	@Test
	void contextLoads() {
		System.out.println("Hello World!");
//		QueryWrapper queryWrapper=QueryWrapper.create().select();
		List<Account> accountList = accountMapper.selectAll();
//
//		System.out.println(JSON.toJSONString(accountList));
//		System.out.println();

	}

}
