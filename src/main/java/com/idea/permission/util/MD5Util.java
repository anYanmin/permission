package com.idea.permission.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String text) {
       return  DigestUtils.md5Hex(text);
    }
}
