package com.dljd.mail.utils;

import lombok.SneakyThrows;

/**
 * Created by jiaozhiguang on 2018/3/14.
 *
 *
 * 在举一个真实一点的例子，如复制对象的属性方法。

 一开始，如果我们自己不定义工具类方法，那么我们可以使用 org.springframework.beans.BeanUtils.copyProperties(source, dest)这个工具类来实现，就一行代码，和调用自己的工具类没有什么区别。看上去很OK，对吧？

 随着业务发展，我们发现这个方式的性能或者某些特性不符合我们要求，我们需要修改改成 commons-beanutils包里面的方法，org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source)，这个时候问题来了，第一个问题，它的方法的参数顺序和之前spring的工具类是相反的，改起来非常容易出错！第二个问题，这个方法有异常抛出，必须声明，这个改起来可要命了！结果你发现，一个看上去很小的改动，改了几十个文件，每个改动还得测试一次，风险不是那么得小。有一点小奔溃了，是不是？

 不要觉得我是凭空想象，编程活久见，你总会遇到的一天！
 */
public class BeanUtils {

    @SneakyThrows
    public static void copyAttribute(Object source, Object dest) {
        org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
    }

}
