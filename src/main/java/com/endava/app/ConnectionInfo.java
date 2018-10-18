package com.endava.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionInfo {
    protected static final String PATH_TO_CONFIG = "target/maven-archiver/pom.properties";

    static private Properties appProprieties = getProprietiesFile();
    protected static final String DRIVER_URL = appProprieties.getProperty("driverUrl");
    protected static final String CONNECTION_URL = appProprieties.getProperty("urlToDatabase");
    protected static final String USERNAME = appProprieties.getProperty("username");
    protected static final String PASSWORD = appProprieties.getProperty("password");

    protected static Properties getProprietiesFile() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(PATH_TO_CONFIG));
            return appProps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return appProps;
    }

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(CONNECTION_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionInfo() {
    }


}
