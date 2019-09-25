package com.imooc.util;

import java.util.Random;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 20:44 2018/10/23
 */
public class KeyUtil {

    //生成唯一的主键:时间加随机数
    public static synchronized String genUniqueKey() {
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
