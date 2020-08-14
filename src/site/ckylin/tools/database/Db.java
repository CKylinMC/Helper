/**
 * @Package: site.ckylin.tools
 * @author: CKylinMC
 * @description:
 * @date: 2020-07-07 9:31
 */
package site.ckylin.tools.database;

import site.ckylin.tools.varutils.ArrayUtils;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库代理类 for MySQL
 */
public class Db {
    private Connection conn;
    private DbSource source = null;
    private String lastSql;
    private int execCount = 0;

    private static Db instance;

    public static Db getLastInstance() {
        if (Db.instance == null) {
            Db.instance = new Db(DbHelper.getLastDBSource());
        }
        return instance;
    }


    /**
     * 使用已有连接初始化代理类
     *
     * @param conn the conn
     */
    public Db(Connection conn) {
        this.conn = conn;
        Db.instance = this;
    }


    /**
     * 使用已有连接初始化代理类
     *
     * @param source the source
     */
    public Db(DbSource source) {
        this.source = source;
        this.conn = source.prepareConnection();
        Db.instance = this;
    }

    /**
     * 使用本地默认地址和端口号初始化新的数据库连接和代理类
     *
     * @param username the username
     * @param password the password
     * @param database the database
     */
    public Db(String username, String password, String database) {
        this.source = new DbSource(username, password);
        this.source.setDbName(database);
        this.conn = this.source.prepareConnection();
        Db.instance = this;
    }

    /**
     * 使用MySQL默认端口号(3306)初始化新的数据库连接和代理类
     *
     * @param username the username
     * @param password the password
     * @param database the database
     * @param hostname the hostname
     */
    public Db(String username, String password, String database, String hostname) {
        this.source = new DbSource(username, password, hostname);
        this.source.setDbName(database);
        this.conn = this.source.prepareConnection();
        Db.instance = this;
    }

    /**
     * 初始化新的数据库连接和代理类
     *
     * @param username the username
     * @param password the password
     * @param database the database
     * @param hostname the hostname
     * @param port     the port
     */
    public Db(String username, String password, String database, String hostname, int port) {
        this.source = new DbSource(username, password, hostname, port);
        this.conn = this.source.prepareConnection();
        Db.instance = this;
    }

    /**
     * 获取数据库连接对象
     *
     * @return the connection
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * 获取数据库源
     *
     * @return the source
     */
    public DbSource getSource() {
        return source;
    }

    public boolean reconnect() {
        if (source != null) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
            conn = source.prepareConnection();
            return true;
        } else return false;
    }

    public boolean ensureConnection() {
        try {
            if (isOk()) {
                return true;
            } else if (source != null) {
                reconnect();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 执行“修改”查询，返回受影响的行数
     *
     * @param SQL 查询语句
     * @return 受影响的行数，-1为出错
     * @throws SQLException 数据库错误
     */
    public int execUpdate(String SQL) throws SQLException {
        if (!ensureConnection()) return -1;
        lastSql = SQL;
        execCount++;
        int res;
        try (Statement stmt = conn.createStatement()) {
            res = stmt.executeUpdate(SQL);
        }
        return res;
    }

    /**
     * 执行“查询”，返回结果
     *
     * @param SQL 查询语句
     * @return 缓存的结果集
     * @throws SQLException 数据库错误
     */
    public CachedRowSet execQuery(String SQL) throws SQLException {
        if (!ensureConnection()) return null;
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

    /**
     * 执行语句，返回执行成功与否
     *
     * @param SQL 查询语句
     * @return 是否成功执行
     * @throws SQLException 数据库错误
     */
    public boolean exec(String SQL) throws SQLException {
        if (!ensureConnection()) return false;
        lastSql = SQL;
        execCount++;
        boolean res;
        try (Statement stmt = conn.createStatement()) {
            res = stmt.execute(SQL);
        }
        return res;
    }

    /**
     * 获取一个新的预查询对象
     *
     * @param sql 要执行的查询语句，可包含占位符
     * @return 预查询对象
     * @throws SQLException 数据库错误
     */
    public PreparedStatement getPreStatement(String sql) throws SQLException {
        if (!ensureConnection()) return null;
        return conn.prepareStatement(sql);
    }

    /**
     * 使用“预查询”方式执行“修改”查询，返回受影响的行数
     *
     * @param SQL    可以包含占位符的查询语句
     * @param params 占位符替换参数
     * @return 受影响的行数
     * @throws SQLException 数据库错误
     */
    public int execUpdate(String SQL, Object... params) throws SQLException {
        if (!ensureConnection()) return -1;
        lastSql = SQL;
        execCount++;
        int res;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i - 1]);
            }
            res = stmt.executeUpdate();
        }
        return res;
    }

    /**
     * 使用“预查询”方式执行查询，返回结果
     *
     * @param SQL    可以包含占位符的查询语句
     * @param params 占位符替换参数
     * @return 缓存的结果集
     * @throws SQLException 数据库错误
     */
    public CachedRowSet execQuery(String SQL, Object... params) throws SQLException {
        if (!ensureConnection()) return null;
        lastSql = SQL;
        execCount++;
        ResultSet res;
        CachedRowSet cachedRowSet;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i - 1]);
            }
            res = stmt.executeQuery();
            cachedRowSet = DbHelper.toRowSet(res);
        }
        return cachedRowSet;
    }

    /**
     * 使用“预查询”方式执行查询，返回执行是否成功的状态
     *
     * @param SQL    可以包含占位符的查询语句
     * @param params 占位符替换参数
     * @return 是否成功执行
     * @throws SQLException 数据库错误
     */
    public boolean exec(String SQL, Object... params) throws SQLException {
        if (!ensureConnection()) return false;
        lastSql = SQL;
        execCount++;
        boolean res;
        try (PreparedStatement stmt = getPreStatement(SQL)) {
            for (int i = 1; i <= params.length; i++) {
                stmt.setObject(i, params[i - 1]);
            }
            res = stmt.execute();
        }
        return res;
    }

    /**
     * 查询所有列
     *
     * @param tableName 数据表名
     * @return 缓存的结果集
     */
    public CachedRowSet SELECT(String tableName) {
        return SELECT("*", tableName, null);
    }

    /**
     * 查询所有数据
     *
     * @param tableName 数据表名
     * @param options   参数
     * @return 缓存的结果集
     */
    public CachedRowSet SELECT(String tableName, String options) {
        return SELECT("*", tableName, options);
    }

    /**
     * 查询数据
     *
     * @param columns   指定数据列
     * @param tableName 数据表名
     * @return 缓存的结果集
     */
    public CachedRowSet SELECT(ArrayList<String> columns, String tableName) {
        return SELECT(columns, tableName, null);
    }

    /**
     * 查询数据
     *
     * @param columns   指定数据列
     * @param tableName 数据表名
     * @param options   参数
     * @return 缓存的结果集
     */
    public CachedRowSet SELECT(ArrayList<String> columns, String tableName, String options) {
        String cols = ArrayUtils.arrayJoin(columns, ",");
        return SELECT(cols, tableName, options);
    }

    /**
     * 查询数据
     *
     * @param columns   指定数据列
     * @param tableName 数据表名
     * @param options   参数
     * @return 缓存的结果集
     */
    public CachedRowSet SELECT(String columns, String tableName, String options) {
        try {
            return execQuery(String.format("SELECT %s from %s %s", columns, tableName, options == null ? "" : options));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入数据
     *
     * @param tableName 数据表名
     * @param datas     数据
     * @return 受影响行数
     */
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

    /**
     * 插入数据
     *
     * @param tableName 数据表名
     * @param columns   指定数据列
     * @param datas     数据
     * @return 受影响行数
     */
    public int INSERT(String tableName, ArrayList<String> columns, ArrayList<String> datas) {
        return INSERT(tableName, ArrayUtils.arrayJoin(columns, ","), datas);
    }

    /**
     * 插入数据
     *
     * @param tableName 数据表名
     * @param columns   指定数据列
     * @param datas     数据
     * @return 受影响行数
     */
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

    /**
     * 更新数据，自动添加引号
     *
     * @param tableName 数据表名
     * @param set       修改数据
     * @return 受影响行数
     */
    public int UPDATE_quoted(String tableName, HashMap<String, String> set) {
        return UPDATE_quoted(tableName, set, "");
    }

    /**
     * 更新数据，自动添加引号
     *
     * @param tableName 数据表名
     * @param set       修改数据
     * @param options   参数
     * @return 受影响行数
     */
    public int UPDATE_quoted(String tableName, HashMap<String, String> set, String options) {
        StringBuilder setstr = new StringBuilder();
        for (Map.Entry<String, String> setItem : set.entrySet()) {
            setstr.append(setItem.getKey()).append("=").append("\"").append(setItem.getValue()).append("\",");
        }
        setstr = new StringBuilder(setstr.substring(0, setstr.length() - 1));
        return UPDATE(tableName, setstr.toString(), options);
    }

    /**
     * 更新数据，不添加引号
     *
     * @param tableName 数据表名
     * @param set       修改数据
     * @return 受影响行数
     */
    public int UPDATE(String tableName, HashMap<String, String> set) {
        return UPDATE(tableName, set, "");
    }

    /**
     * 更新数据，不添加引号
     *
     * @param tableName 数据表名
     * @param set       修改数据
     * @param options   参数
     * @return 受影响行数
     */
    public int UPDATE(String tableName, HashMap<String, String> set, String options) {
        StringBuilder setstr = new StringBuilder();
        for (Map.Entry<String, String> setItem : set.entrySet()) {
            setstr.append(setItem.getKey()).append("=").append(setItem.getValue()).append(",");
        }
        setstr = new StringBuilder(setstr.substring(0, setstr.length() - 1));
        return UPDATE(tableName, setstr.toString(), options);
    }

    /**
     * 更新数据
     *
     * @param tableName 数据表名
     * @param set       修改数据
     * @param options   参数
     * @return 受影响行数
     */
    public int UPDATE(String tableName, String set, String options) {
        try {
            return execUpdate(String.format("UPDATE SET %s %s %s", tableName, set, options == null ? "" : "WHERE " + options));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 删除所有数据
     *
     * @param tableName 数据表名
     * @return 受影响行数
     */
    public int DELETE(String tableName) {
        return DELETE(tableName, "");
    }

    /**
     * 删除数据
     *
     * @param tableName 数据表名
     * @param where     限定条件
     * @return 受影响行数
     */
    public int DELETE(String tableName, String where) {
        try {
            return execUpdate(String.format("DELETE FROM %s %s", tableName, where != null && !where.equals("") ? "WHERE " + where : ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 全表计数
     *
     * @param tableName 数据表名
     * @return 行数
     */
    public int count(String tableName) {
        return count(tableName, "*");
    }

    /**
     * 指定列序号，计数
     *
     * @param tableName   数据表名
     * @param columnIndex 列编号
     * @return 行数
     */
    public int count(String tableName, int columnIndex) {
        return count(tableName, "" + columnIndex);
    }

    /**
     * 指定列计数
     *
     * @param tableName  数据表名
     * @param columnName 列名或编号
     * @return 行数
     */
    public int count(String tableName, String columnName) {
        try {
            ResultSet rs = SELECT("COUNT(" + columnName + ")", tableName, "");
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            if (isOk()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回连接是否有效
     *
     * @return 是否有效
     * @throws SQLException 数据库错误
     */
    public boolean isOk() throws SQLException {
        return conn != null && !conn.isClosed();
    }

    /**
     * Gets last sql.
     *
     * @return the last sql
     */
    public String getLastSql() {
        return lastSql;
    }

    /**
     * Gets exec count.
     *
     * @return the exec count
     */
    public int getExecCount() {
        return execCount;
    }

    protected void finalize() {
        close();
    }
}
