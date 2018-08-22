package com.metalogin.utils;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * created by yumira 2018/7/27.
 */
public class Cryptor {
    public static String sha256(String input) {
        return DigestUtil.sha256Hex(input);
    }

}
