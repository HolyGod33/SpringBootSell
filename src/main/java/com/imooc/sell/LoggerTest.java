package com.imooc.sell;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 18:08 2018/10/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Data
public class LoggerTest {

    //创建日志对象  反射创建对象  当前
    //为什么用LoggerTest.class?因为在下列输出日志时需要定定位所在类的日志 com.imooc.sell.LoggerTest
    //private final Logger logger=LoggerFactory.getLogger(LoggerTest.class);
    //使用lombok则可以将以上代码省略

    @Test
    public void test1(){
        String name="imooc";
        //logger.debug("");
        log.debug("debug...");//代码省略的结果
        log.info("info....");
        log.error("error...");
        log.info("name:{}",name);//lombok的优化结果,{}为占位符
        //注意看application的配置,里面有配置日志输出格式
    }
}
