package com.endava.app.sql.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

public interface eStatementInterface extends statement{

    default void executeStatement(Connection connObj, String command){
        Statement pstmtObj = null;
        try {
            pstmtObj = connObj.createStatement();
            pstmtObj.executeUpdate(command);

            connObj.commit();
            System.out.println("\n=====Success=====\n");
        } catch (SQLSyntaxErrorException sqlException) {
            System.out.println("\n=====SQL Syntax Error!=====\n");
        } catch (SQLException sqlException) {
            System.out.println("\n=====SQL Exception!=====\n");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                // Closing PreparedStatement Object
                if (pstmtObj != null) {
                    pstmtObj.close();
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}
