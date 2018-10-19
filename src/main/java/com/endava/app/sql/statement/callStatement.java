package com.endava.app.sql.statement;

import java.sql.Connection;

@FunctionalInterface
public interface callStatement {
    void call(Connection connObj, String... args);
}
