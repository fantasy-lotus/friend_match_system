package com.lotus;

import com.lotus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootStartApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {

    }

}
