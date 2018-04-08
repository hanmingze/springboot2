package com.dljd.mail.utils;

/**
 * Created by jiaozhiguang on 2018/3/14.
 *
 * 隐藏实现
 *
 * 定义自己的工具类，尽量不要在业务代码里面直接调用第三方的工具类 ,解耦的一种体现
 *
 1,不同的人会使用不同的第三方工具库，会比较乱。
 2,将来万一要修改工具类的实现逻辑会很痛苦。

 以最简单的字符串判空为例，很多工具库都有 StringUtils工具类，如果我们使用commons的工具类，一开始我们直接使用 StringUtils.isEmpty ，字符串为空或者空串的时候会返回为true，后面业务改动，需要改成如果全部是空格的时候也会返回true，怎么办？我们可以改成使用 StringUtils.isBlank 。看上去很简单，对吧？ 如果你有几十个文件都调用了，那我们要改几十个文件，是不是有点恶心？再后面发现，不只是英文空格，如果是全角的空格，也要返回为true，怎么办？StringUtils上的方法已经不能满足我们的需求了，真不好改了。



 */
public class StringUtil {

//    public static boolean isEmpty(String str) {
//        return org.apache.commons.lang3.StringUtils.isEmpty(str);
//    }

    // 使用父类/接口
    public static boolean isEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return ! org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }


}
