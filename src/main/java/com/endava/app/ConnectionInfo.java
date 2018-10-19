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

        try {
            Properties appProps = new Properties();
            appProps.load(new FileInputStream(PATH_TO_CONFIG));
            return appProps;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
