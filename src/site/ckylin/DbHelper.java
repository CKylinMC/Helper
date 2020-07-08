/**
 * @Package: site.ckylin
 * @author: CKylinMC
 * @description:
 * @date: 2020-07-07 9:16
 */
package site.ckylin;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbHelper {
    public static boolean loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String buildJDBCUrl(String driver, String host, int port, String databaseName) {
        return "jdbc:" + driver + "://" + host + ":" + port + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
    }

    public static Connection initConnection(String username, String password, String databaseName) {
        return initConnection(username, password, databaseName, "127.0.0.1", 3306);
    }

    public static Connection initConnection(String username, String password, String databaseName, String hostname) {
        return initConnection(username, password, databaseName, hostname, 3306);
    }

    public static Connection initConnection(String username, String password, String databaseName, String hostname, int port) {
        try {
            return DriverManager.getConnection(buildJDBCUrl("mysql", hostname, port, databaseName), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CachedRowSet toRowSet(ResultSet rs) {
        try {
            CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.populate(rs);
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSelectCmd(String tableName) {
        return getSelectCmd(tableName, "*");
    }

    public static String getSelectCmd(String tableName, String... columns) {
        String baseCmd = "SELECT <COLS> FROM <TBNAME> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        StringBuilder cols = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String delimiter = i == columns.length - 1 ? "" : ",";
            cols.append(columns[i]).append(delimiter);
        }
        if (cols.length() > 0)
            cmd = cmd.replace("<COLS>", "(" + cols + ")");
        else
            cmd = cmd.replace("<COLS>", "");
        return cmd;
    }

    public static String getInsertCmd(String tableName, String... columns) {
        String baseCmd = "INSERT INTO <TBNAME> <COLS> VALUES (<VALS>) ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        StringBuilder cols = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String delimiter = i == columns.length - 1 ? "" : ",";
            cols.append(columns[i]).append(delimiter);
        }
        if (cols.length() > 0)
            cmd = cmd.replace("<COLS>", "(" + cols + ")");
        else
            cmd = cmd.replace("<COLS>", "");
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String queryPlaceHolder = i == columns.length - 1 ? "?" : "?,";
            placeholders.append(queryPlaceHolder);
        }
        cmd = cmd.replace("<VALS>", placeholders.toString());
        return cmd;
    }

    public static String getUpdateCmd(String tableName, String... changedColumns) {
        String baseCmd = "UPDATE <TBNAME> SET <COLS> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        StringBuilder cols = new StringBuilder();
        for (int i = 0; i < changedColumns.length; i++) {
            String delimiter = i == changedColumns.length - 1 ? "=?" : "=?,";
            cols.append(changedColumns[i]).append(delimiter);
        }
        if (cols.length() > 0)
            cmd = cmd.replace("<COLS>", "(" + cols + ")");
        else
            cmd = cmd.replace("<COLS>", "");
        return cmd;
    }

    public static String getDeleteCmd(String tableName) {
        String baseCmd = "DELETE FROM <TBNAME> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        return cmd;
    }

    public static String where(String limits) {
        return "WHERE " + limits + " ";
    }
}

