package com.endava.app.sql.statement;

import java.sql.Connection;

public interface eStoredProcedureInterface {
    void executeStoredProcedure(Connection connObj, String command);
}
