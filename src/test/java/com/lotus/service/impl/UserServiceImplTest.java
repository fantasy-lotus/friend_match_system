package com.lotus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.lotus.pojo.User;
import com.lotus.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    public void testInsert() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 4; i++) {
            List<User> list = new ArrayList<>();
            for (int j = 0; j < 500000; j++) {
                User user = new User();
                user.setUname("fake");
                user.setTel("13388888888");
                user.setPwd("111111");
                user.setEmail("1");
                user.setGender(0);
                user.setUserStatus(0);
                user.setTags("");
                user.setRole(0);
                user.setIsDelete(0);
                list.add(user);
            }
            userService.saveBatch(list);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    void redisTest(){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set("test","test");
        Object o = operations.get("test");
        Assertions.assertEquals("test",o);
    }
}