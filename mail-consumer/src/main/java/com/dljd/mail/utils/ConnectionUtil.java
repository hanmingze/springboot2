package com.dljd.mail.utils;

import java.util.Collection;

/**
 * Created by jiaozhiguang on 2018/3/14.
 */
public class ConnectionUtil {

    //    public static boolean isEmpty(ArrayList<?> list) {
//        return list == null || list.size() == 0;
//    }
//
//    public static boolean isEmpty(List<?> list) {
//        return list == null || list.size() == 0;
//    }
//
//    public static boolean isEmpty(Collection<?> list) {
//        return list == null || list.size() == 0;
//    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

}
