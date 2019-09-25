package com.imooc.util;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 10:44 2019/8/5
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     *=============================
     * @author: JXUT CXY
     * @Date: 10:48 2019/8/5
     * @param d1
     * @param d2
     * @return:
     * create with IDEA
     *=============================
     */
    public static Boolean equals(Double d1, Double d2){
        Double result = Math.abs(d1 - d2);

        if (result < MONEY_RANGE) {
            return true;
        }else {
            return false;
        }
    }
}
