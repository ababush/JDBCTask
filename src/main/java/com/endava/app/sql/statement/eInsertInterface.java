package com.endava.app.sql.statement;

import java.sql.Connection;

public interface eInsertInterface extends statement {

     void executeInsertIntoTable(Connection connObj, String... args);
}
