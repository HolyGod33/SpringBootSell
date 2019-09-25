package com.imooc.util;

import com.imooc.enums.CodeEnum;

/**
 * @Author JXUT CXY
 * @Description
 * @Date: created in 11:43 2019/8/9
 */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for(T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
