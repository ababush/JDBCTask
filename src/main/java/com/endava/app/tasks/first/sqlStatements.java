package com.endava.app.tasks.first;

public class sqlStatements {

    protected static String getSelectStatementString() {

        return "SELECT E.FIRST_NAME , E.LAST_NAME , E.DEPARTMENT_ID, D.DEPARTMENT_NAME " +
                "FROM HR.EMPLOYEES E " +
                "     LEFT JOIN HR.DEPARTMENTS D " +
                "            ON E.DEPARTMENT_ID = D.DEPARTMENT_ID";
    }


    protected static String getCreateTableStatement() {

        return "  CREATE TABLE new_table(" +
                "      id_new_table  NUMBER(10) PRIMARY KEY," +
                "      int_value NUMBER(5)," +
                "      description BLOB," +
                "      date_value DATE," +
                "      timestamp_value TIMESTAMP WITH LOCAL TIME ZONE " +
                "    )";
    }

    protected static String getInsertStatement() {
        return " INSERT INTO new_table(id_new_table, int_value, description,  date_value,  timestamp_value)\n" +
                " VALUES (?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), ?)";
    }

    protected static String getAlterTableStatement() {
        return " ALTER TABLE new_table" +
                " ADD new_value varchar(5)";
    }

    protected static String getDropTableStatement() {
        return " DROP TABLE new_table";
    }

    protected static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}
