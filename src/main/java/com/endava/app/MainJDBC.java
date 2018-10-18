package com.endava.app;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;


public class MainJDBC {

    private static GenericObjectPool gPool = null;

// Connection

    public static void executeStatement(String command, Connection connObj) {
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

    public static void executeSelect(Connection connObj) {
        ResultSet rsObj = null;
        PreparedStatement pstmtObj = null;
        try {
            pstmtObj = connObj.prepareStatement(getSelectStatementString());
            rsObj = pstmtObj.executeQuery();
            System.out.println("\n=====The first name, last name, department number, and department name for each employee=====\n");
            while (rsObj.next()) {
                System.out.println(rsObj.getString("FIRST_NAME") + "\t" +
                        rsObj.getString("LAST_NAME") + "\t" +
                        rsObj.getString("DEPARTMENT_ID") + "\t" +
                        rsObj.getString("DEPARTMENT_NAME"));
            }
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                // Closing ResultSet Object
                if (rsObj != null) {
                    rsObj.close();
                }
                // Closing PreparedStatement Object
                if (pstmtObj != null) {
                    pstmtObj.close();
                }
            } catch (Exception sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static void executeInsertIntoTable(Connection connObj) {

        PreparedStatement pstmtObj = null;
        try {

            File blob = new File("src/main/files/1.jpg");
            FileInputStream in = new FileInputStream(blob);

            pstmtObj = connObj.prepareStatement(getInsertStatement());
            pstmtObj.setInt(1, 101);
            pstmtObj.setInt(2, 11);
            pstmtObj.setBinaryStream(3, in, (int) blob.length());
            pstmtObj.setString(4, "2018-04-17");
            pstmtObj.setTimestamp(5, getCurrentTimeStamp());

            // execute insert SQL stetement
            pstmtObj.executeUpdate();

            connObj.commit();

            System.out.println("\n=====Success=====\n");
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
        Connection connObj = null;
        MainJDBC jdbcObj = new MainJDBC();
        try {
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println("\n=====Making A New Connection Object=====\n");
            connObj = dataSource.getConnection();
            connObj.setAutoCommit(false);
            jdbcObj.callAllMethodsForJDBC(connObj);

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
        }
    }

    // Sql statements

    private static String getSelectStatementString() {

        return "SELECT E.FIRST_NAME , E.LAST_NAME , E.DEPARTMENT_ID, D.DEPARTMENT_NAME " +
                "FROM HR.EMPLOYEES E " +
                "     LEFT JOIN HR.DEPARTMENTS D " +
                "            ON E.DEPARTMENT_ID = D.DEPARTMENT_ID";
    }

    private static String getCreateTableStatement() {

        return "  CREATE TABLE new_table(" +
                "      id_new_table  NUMBER(10) PRIMARY KEY," +
                "      int_value NUMBER(5)," +
                "      description BLOB," +
                "      date_value DATE," +
                "      timestamp_value TIMESTAMP WITH LOCAL TIME ZONE " +
                "    )";
    }

    private static String getInsertStatement() {
        return " INSERT INTO new_table(id_new_table, int_value, description,  date_value,  timestamp_value)\n" +
                " VALUES (?, ?, ?, TO_DATE(?,'YYYY-MM-DD'), ?)";
    }

    private static String getAlterTableStatement() {
        return " ALTER TABLE new_table" +
                " ADD new_value varchar(5)";
    }

    private static String getDropTableStatement() {
        return " DROP TABLE new_table";
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

    public DataSource setUpPool() throws Exception {
        Class.forName(ConnectionInfo.DRIVER_URL);

        // Creates an Instance of GenericObjectPool That Holds Our Pool of Connections Object!
        gPool = new GenericObjectPool();
        gPool.setMaxActive(5);

        // Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create the Connection Object!
        ConnectionFactory cf = new DriverManagerConnectionFactory(ConnectionInfo.CONNECTION_URL, ConnectionInfo.USERNAME, ConnectionInfo.PASSWORD);

        // Creates a PoolableConnectionFactory That Will Wraps the Connection Object Created by the ConnectionFactory to Add Object Pooling Functionality!
        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    public GenericObjectPool getConnectionPool() {
        return gPool;
    }

}
