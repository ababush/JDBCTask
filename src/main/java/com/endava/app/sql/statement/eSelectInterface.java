package com.endava.app.sql.statement;

import java.sql.Connection;

public interface eSelectInterface extends statement{
    void executeSelect(Connection connObj);
}
