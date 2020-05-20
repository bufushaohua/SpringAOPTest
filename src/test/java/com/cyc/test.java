package com.cyc;

import com.cyc.service.AccountService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试AOP的配置
 */
public class test {
    @Test
    public void test(){
        //1.获取容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        //2.获取对象
        AccountService accountService = (AccountService)applicationContext.getBean("accountService");
        //3.执行方法
        accountService.saveAccount();
    }


}

