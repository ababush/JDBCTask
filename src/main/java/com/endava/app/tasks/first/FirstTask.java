package com.endava.app.tasks.first;

import com.endava.app.setUpConnPool;
import com.endava.app.sql.statement.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.concurrent.Callable;

import static com.endava.app.tasks.first.sqlStatements.*;

public class FirstTask implements callingMethods, eSelectInterface, eStatementInterface, eInsertInterface, eStoredProcedureInterface {

    @Override
    public void executeSelect(Connection connObj) {
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


    @Override
    public void executeInsertIntoTable(Connection connObj, String... args) {
        PreparedStatement pstmtObj = null;
        try {

            File blob = new File(args[2]);
            FileInputStream in = new FileInputStream(blob);

            pstmtObj = connObj.prepareStatement(getInsertStatement());
            pstmtObj.setInt(1, Integer.parseInt(args[0]));
            pstmtObj.setInt(2, Integer.parseInt(args[1]));
            pstmtObj.setBinaryStream(3, in, (int) blob.length());
            pstmtObj.setString(4, args[3]);
            pstmtObj.setTimestamp(5, getCurrentTimeStamp());

            // execute insert SQL statement
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


    @Override
    public void executeStoredProcedure(Connection connObj, String command) {
        CallableStatement pstmtObj = null;
        try {
            int inputNumber = 7, resustOfStoredProc = 0;

            pstmtObj = connObj.prepareCall(command);
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



    public void callAllMethodsForTask(Connection connObj) {

        System.out.println("\n=====Executing select from db=====\n");
        executeSelect(connObj);
        System.out.println("\n=====Creating new table=====\n");
        executeStatement(connObj, getCreateTableStatement()); // create table
        System.out.println("\n=====Executing insert into table=====\n");
        executeInsertIntoTable(connObj, "14", "145", "src/main/files/2.jpg", "2002-12-12"); // insert into table
        System.out.println("\n=====Executing alter table statement=====\n");
        executeStatement(connObj, getAlterTableStatement()); // execute alter table
        System.out.println("\n===== Drop table =====\n");
        executeStatement( connObj, getDropTableStatement()); // drop table
        System.out.println("\n===== Executing stored procedure =====\n");
        executeStoredProcedure(connObj, "{call MULTIPLY(?, ?)}"); //execute stored procedure

    }


    public static void main(String[] args) {
        Connection connObj = null;
        FirstTask jdbcObj = new FirstTask();
        try {
            DataSource dataSource = new setUpConnPool().setUpPool();
            System.out.println("\n=====Making A New Connection Object=====\n");
            connObj = dataSource.getConnection();
            connObj.setAutoCommit(false);
            jdbcObj.callAllMethodsForTask(connObj);

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

}
