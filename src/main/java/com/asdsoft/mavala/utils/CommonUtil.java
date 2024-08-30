package com.asdsoft.mavala.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;

public class CommonUtil {

    private CommonUtil() {
    }

    public static boolean isNullOrEmpty(Map<?, ?> obj) {
        return null == obj || CollectionUtils.isEmpty(obj);
    }

    public static boolean isNullOrEmpty(Collection<?> obj) {
        return null == obj || CollectionUtils.isEmpty(obj);
    }

    public static boolean isNullOrEmpty(String string) {
        return null == string || StringUtils.isEmpty(string);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return null == obj || ObjectUtils.isEmpty(obj);
    }
    public static BigDecimal convertToBigDecimal(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

}
