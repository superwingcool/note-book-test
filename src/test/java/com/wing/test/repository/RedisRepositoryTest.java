package com.wing.test.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisRepositoryTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void test_set() {
        redisRepository.set("1", "1");
    }

}