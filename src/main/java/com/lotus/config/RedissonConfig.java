package com.lotus.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RedissonConfig {
    @Value("${server_profile.ip}")
    private String ip;

    @Bean(destroyMethod="shutdown") // 服务停止后调用 shutdown 方法。
    public RedissonClient redisson(){
        // 1.创建配置
        Config config = new Config();
        // 集群模式
        // config.useClusterServers()
        // 2.根据 Config 创建出 RedissonClient 示例。
        String address = "redis://"+ip+":6379";
        config.useSingleServer().setAddress(address).setDatabase(3);
        return Redisson.create(config);
    }
}

