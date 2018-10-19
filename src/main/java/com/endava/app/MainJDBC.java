package com.endava.app;

import java.sql.*;
import java.util.stream.Stream;


public class MainJDBC {




    public static void executeStoredProcedure(Connection connObj) {
        CallableStatement pstmtObj = null;
        try {
            int inputNumber = 7, resustOfStoredProc = 0;

            pstmtObj = connObj.prepareCall("{call MULTIPLY(?, ?)}");
            pstmtObj.setInt(1, inputNumber);
            pstmtObj.registerOutParameter(2, Types.INTEGER);
            pstmtObj.executeQuery();

            resustOfStoredProc = pstmtObj.getInt(2);

            System.out.println("\n===== Stored procedure execution:  " + inputNumber + " * 2 = " + resustOfStoredProc + " =====\n");

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

    private void callAllMethodsForJDBC(Connection connObj) {

//        System.out.println("\n=====Executing select from db=====\n");
//        executeSelect(connObj); // select
//        System.out.println("\n=====Creating new table=====\n");
//        executeStatement(getCreateTableStatement(), connObj); // create table
//        System.out.println("\n=====Executing insert into table=====\n");
//        executeInsertIntoTable(connObj); // insert into table
//        System.out.println("\n=====Executing alter table statement=====\n");
//        executeStatement(getAlterTableStatement(), connObj); // execute alter table
//        System.out.println("\n=====Drop table=====\n");
//        executeStatement(getDropTableStatement(), connObj); // drop table
//        System.out.println("\n=====Executing stored procedure=====\n");
//        executeStoredProcedure(connObj); //execute stored procedure

    }

    public static void main(String[] args) {
      /*  Connection connObj = null;
        MainJDBC jdbcObj = new MainJDBC();
        try {
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println("\n=====Making A New Connection Object=====\n");
            connObj = dataSource.getConnection();
            connObj.setAutoCommit(false);
           // executeSelect(connObj);

        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                // Closing Connection Object
                if (connObj != null) {
                    connObj.close();
                    System.out.println("\n=====Connection is closed=====\n");
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
        }*/
    }

    // Sql statements




    protected static String getSelectStatementString() {

        return "SELECT E.FIRST_NAME , E.LAST_NAME , E.DEPARTMENT_ID, D.DEPARTMENT_NAME " +
                "FROM HR.EMPLOYEES E " +
                "     LEFT JOIN HR.DEPARTMENTS D " +
                "            ON E.DEPARTMENT_ID = D.DEPARTMENT_ID";
    }
}
