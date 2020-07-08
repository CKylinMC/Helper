/**
 * @Package: site.ckylin
 * @author: CKylinMC
 * @description:
 * @date: 2020-07-07 9:31
 */
package site.ckylin;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Db {
    private Connection conn;
    private String lastSql;
    private int execCount = 0;

    public Db(Connection conn) {
        this.conn = conn;
    }

    public Db(String username, String password, String database) {
        this.conn = DbHelper.initConnection(username, password, database);
    }

    public Db(String username, String password, String database, String hostname) {
        this.conn = DbHelper.initConnection(username, password, database, hostname);
    }

    public Db(String username, String password, String database, String hostname, int port) {
        this.conn = DbHelper.initConnection(username, password, database, hostname, port);
    }

    public Connection getConnection() {
        return conn;
    }

    public int execUpdate(String SQL) throws SQLException {
        if (!isOk()) return -1;
        lastSql = SQL;
        execCount++;
        int res;
        try (Statement stmt = conn.createStatement()) {
            res = stmt.executeUpdate(SQL);
        }
        return res;
    }

    public CachedRowSet execQuery(String SQL) throws SQLException {
        if (!isOk()) return null;
        lastSql = SQL;
        execCount++;
        ResultSet res;
        CachedRowSet cachedRowSet;
        try (Statement stmt = conn.createStatement()) {
            res = stmt.executeQuery(SQL);
            cachedRowSet = DbHelper.toRowSet(res);
        }
        return cachedRowSet;
    }

    public boolean exec(String SQL) throws SQLException {
        if (!isOk()) return false;
        lastSql = SQL;
        execCount++;
        boolean res;
        try (Statement stmt = conn.createStatement()) {
            res = stmt.execute(SQL);
        }
        return res;
    }

    public PreparedStatement getPreStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public int execUpdate(String SQL, Object[] params) throws SQLException {
        if (!isOk()) return -1;
        lastSql = SQL;
        execCount++;
        int res;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i]);
            }
            res = stmt.executeUpdate(SQL);
        }
        return res;
    }

    public CachedRowSet execQuery(String SQL, Object[] params) throws SQLException {
        if (!isOk()) return null;
        lastSql = SQL;
        execCount++;
        ResultSet res;
        CachedRowSet cachedRowSet;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i]);
            }
            res = stmt.executeQuery(SQL);
            cachedRowSet = DbHelper.toRowSet(res);
        }
        return cachedRowSet;
    }

    public boolean exec(String SQL, Object[] params) throws SQLException {
        if (!isOk()) return false;
        lastSql = SQL;
        execCount++;
        boolean res;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i]);
            }
            res = stmt.execute(SQL);
        }
        return res;
    }

    public CachedRowSet SELECT(String tableName) {
        return SELECT("*", tableName, null);
    }

    public CachedRowSet SELECT(String tableName, String options) {
        return SELECT("*", tableName, options);
    }

    public CachedRowSet SELECT(ArrayList<String> columns, String tableName) {
        return SELECT(columns, tableName, null);
    }

    public CachedRowSet SELECT(ArrayList<String> columns, String tableName, String options) {
        String cols = Helper.arrayJoin(columns, ",");
        return SELECT(cols, tableName, options);
    }

    public CachedRowSet SELECT(String columns, String tableName, String options) {
        try {
            return execQuery(String.format("SELECT %s from %s %s", columns, tableName, options == null ? "" : options));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int INSERT(String tableName, ArrayList<String> datas) {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            data.append("(").append(datas.get(i)).append(")").append(i == datas.size() - 1 ? "" : ",");
        }
        try {
            return execUpdate(String.format("INSERT INTO %s VALUES %s", tableName, data.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int INSERT(String tableName, ArrayList<String> columns, ArrayList<String> datas) {
        return INSERT(tableName, Helper.arrayJoin(columns, ","), datas);
    }

    public int INSERT(String tableName, String columns, ArrayList<String> datas) {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            data.append("(").append(datas.get(i)).append(")").append(i == datas.size() - 1 ? "" : ",");
        }
        try {
            return execUpdate(String.format("INSERT INTO %s (%s) VALUES %s", tableName, columns, data.toString()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int UPDATE_quoted(String tableName, HashMap<String, String> set) {
        return UPDATE_quoted(tableName, set, "");
    }

    public int UPDATE_quoted(String tableName, HashMap<String, String> set, String options) {
        StringBuilder setstr = new StringBuilder();
        for (Map.Entry<String, String> setItem : set.entrySet()) {
            setstr.append(setItem.getKey()).append("=").append("\"").append(setItem.getValue()).append("\",");
        }
        setstr = new StringBuilder(setstr.substring(0, setstr.length() - 1));
        return UPDATE(tableName, setstr.toString(), options);
    }

    public int UPDATE(String tableName, HashMap<String, String> set) {
        return UPDATE(tableName, set, "");
    }

    public int UPDATE(String tableName, HashMap<String, String> set, String options) {
        StringBuilder setstr = new StringBuilder();
        for (Map.Entry<String, String> setItem : set.entrySet()) {
            setstr.append(setItem.getKey()).append("=").append(setItem.getValue()).append(",");
        }
        setstr = new StringBuilder(setstr.substring(0, setstr.length() - 1));
        return UPDATE(tableName, setstr.toString(), options);
    }

    public int UPDATE(String tableName, String set, String options) {
        try {
            return execUpdate(String.format("UPDATE SET %s %s %s", tableName, set, options == null ? "" : "WHERE " + options));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int DELETE(String tableName) {
        return DELETE(tableName, "");
    }

    public int DELETE(String tableName, String where) {
        try {
            return execUpdate(String.format("DELETE FROM %s %s", tableName, where != null && !where.equals("") ? "WHERE " + where : ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void close() {
        try {
            if (isOk()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isOk() throws SQLException {
        return conn != null && !conn.isClosed();
    }

    public String getLastSql() {
        return lastSql;
    }

    public int getExecCount() {
        return execCount;
    }
}
