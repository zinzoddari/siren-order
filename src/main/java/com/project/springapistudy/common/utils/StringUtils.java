package com.project.springapistudy.common.utils;

import org.springframework.util.ObjectUtils;

public class StringUtils {
    /**
     * 입력값이 비어있지 않은지 확인
     */
    public static boolean isNotEmpty(String str) {
        return !ObjectUtils.isEmpty(str) && !str.isBlank();
    }
}
