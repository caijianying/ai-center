package com.xiaobaicai.ai.center.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author caijy
 * @description TODO
 * @date 2023/8/20 星期日 5:59 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSave(){
        UserDO userDO = new UserDO();
        userDO.setUserName("xiaobaicai");
        userMapper.insert(userDO);
    }

    @Test
    public void testQuery(){
        UserDO userDO = userMapper.selectById(1L);
        System.out.println(userDO);
    }
}
