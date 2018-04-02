package com.dljd.mail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by jiaozhiguang on 2018/3/13.
 */
@Component
@Data
public class DBConfig {

    @Value("${table.count}")
    private int mailTableCount;

}
