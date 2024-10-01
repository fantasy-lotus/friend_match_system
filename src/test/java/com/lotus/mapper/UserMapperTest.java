package com.lotus.mapper;

import com.lotus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Test
    public void test(){
        User user = new User();
        user.setUid(1);
        user.setEmail("1");
        user.setEmail(null);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }
}