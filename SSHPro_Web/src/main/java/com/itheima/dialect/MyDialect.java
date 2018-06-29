package com.itheima.dialect;

import org.hibernate.dialect.MySQL5Dialect;

public class MyDialect extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
