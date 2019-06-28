package com.renzo.ordersystem.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private final static String salt = "1a2b3c4d";
    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dBPass = formPassToDBPass(formPass, saltDB);
        return dBPass;
    }
    public static String inputPassToFormPass(String inputPass) {
        String formPass = "" + salt.charAt(0) + salt.charAt(1) + inputPass + salt.charAt(5) + salt.charAt(7);
        return md5(formPass);
    }
    public static String formPassToDBPass(String formPass, String saltDB) {
        String dBPass = "" + saltDB.charAt(0) + saltDB.charAt(1) + formPass + saltDB.charAt(5) + saltDB.charAt(7);
        return md5(dBPass);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("12345", "1a2b3c4d"));
        // System.out.println(inputPassToFormPass("12345")); //8e812d5f58afd2d54f56c691dc8dc2cd
    }
}
