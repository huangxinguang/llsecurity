package com.laile.security.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by huangxinguang on 2017/6/29 上午11:46.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if(str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
