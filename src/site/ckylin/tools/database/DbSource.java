/**
 * @Package: site.ckylin.tools.database
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-14 9:48
 */
package site.ckylin.tools.database;

import site.ckylin.tools.varutils.Converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class DbSource {

    private String hostname = "127.0.0.1";
    private int port = 3306;
    private String username;
    private String password;
    private boolean dbLimited = false;
    private String dbName;
    private String sqlDriver = "mysql";

    public static DbSource buildFromURI(String URI, String username, String password) {
        Map<String, String> config = DbHelper.simpleURIParser(URI);
        String hostname =
                config.containsKey("hostname")
                        ? config.get("hostname")
                        : "127.0.0.1";
        String port =
                config.containsKey("port")
                        ? config.get("port")
                        : "3306";
        String driver =
                config.containsKey("driver")
                        ? config.get("driver")
                        : "mysql";
        String dbName =
                config.containsKey("dbName")
                        ? config.get("dbName")
                        : "";
        DbSource dbSource = new DbSource(username, password, hostname, port);
        dbSource.setSqlDriver(driver);
        if (dbName != null && !Objects.equals(dbName, "")) {
            dbSource.setDbName(dbName);
        }
        return dbSource;
    }

    public DbSource() {

    }

    public DbSource(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public DbSource(String username, String password, String hostname) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public DbSource(String username, String password, String hostname, String port) {
        this.hostname = hostname;
        this.port = Converter.str2int(port, 3306);
        this.username = username;
        this.password = password;
    }

    public DbSource(String username, String password, String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public boolean isDbLimited() {
        return dbLimited;
    }

    public void setDbLimited(boolean dbLimited) {
        this.dbLimited = dbLimited;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
        setDbLimited(true);
    }

    public String getSqlDriver() {
        return sqlDriver;
    }

    public void setSqlDriver(String sqlDriver) {
        this.sqlDriver = sqlDriver;
    }

    public Connection prepareConnection() {
        String uri;
        DbHelper.loadDriver();
        try {
            if (isDbLimited()) {
                uri = DbHelper.buildJDBCUrl(sqlDriver, hostname, port, dbName);
            } else {
                uri = DbHelper.buildJDBCUrl(sqlDriver, hostname, port);
            }
            return DriverManager.getConnection(uri, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getURI() {
        if (isDbLimited())
            return DbHelper.buildJDBCUrl(sqlDriver, hostname, port, dbName);
        else
            return DbHelper.buildJDBCUrl(sqlDriver, hostname, port);
    }
}
