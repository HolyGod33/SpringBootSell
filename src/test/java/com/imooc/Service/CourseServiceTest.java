package com.imooc.Service;

import com.imooc.config.CourseUrlConfig;
import com.imooc.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:14 2019/9/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Slf4j
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUrlConfig courseUrlConfig;

    @Test
    public void search() throws IOException {
        String result = HttpRequestUtil.verifyCodeRecognize(courseUrlConfig.getVerifyImgDestURL());//该验证码识别api有问题  识别率极差

        log.info(result);

    }
}