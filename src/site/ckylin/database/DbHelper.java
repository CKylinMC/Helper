/**
 * @Package: site.ckylin
 * @author: CKylinMC
 * @description:
 * @date: 2020-07-07 9:16
 */
package site.ckylin.database;

import site.ckylin.IO.PropUtils;
import site.ckylin.varutils.Converter;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库工具类 for MySQL
 */
public class DbHelper {

    /**
     * 记录最后一次的连接
     */
    private static Connection lastDBConnection = null;

    /**
     * 加载 MySQL 驱动
     *
     * @return the boolean
     */
    public static boolean loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 构建 JDBC URL, 并使用 UTF-8 编码
     *
     * @param driver       驱动名(如: mysql)
     * @param host         主机地址
     * @param port         端口(MySQL数据库默认端口号为: 3306)
     * @param databaseName 欲连接的数据库名
     * @return 已构建的 JDBC URL
     */
    public static String buildJDBCUrl(String driver, String host, int port, String databaseName) {
        return "jdbc:" + driver + "://" + host + ":" + port + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
    }

    /**
     * 初始化一个数据库连接，使用本地默认地址和端口号(127.0.0.1:3306)。
     *
     * @param username     用户名
     * @param password     密码
     * @param databaseName 欲连接的数据库名
     * @return 数据库连接
     */
    public static Connection initConnection(String username, String password, String databaseName) {
        return initConnection(username, password, databaseName, "127.0.0.1", 3306);
    }

    /**
     * 初始化一个数据库连接，使用默认端口号(3306)
     *
     * @param username     用户名
     * @param password     密码
     * @param databaseName 欲连接的数据库名
     * @param hostname     主机地址
     * @return 数据库连接
     */
    public static Connection initConnection(String username, String password, String databaseName, String hostname) {
        return initConnection(username, password, databaseName, hostname, 3306);
    }

    /**
     * 初始化一个数据库连接
     *
     * @param username     用户名
     * @param password     密码
     * @param databaseName 欲连接的数据库名
     * @param hostname     主机地址
     * @param port         端口号(MySQL默认端口号为: 3306)
     * @return 数据库连接
     */
    public static Connection initConnection(String username, String password, String databaseName, String hostname, int port) {
        loadDriver();
        try {
            lastDBConnection = DriverManager.getConnection(buildJDBCUrl("mysql", hostname, port, databaseName), username, password);
            return lastDBConnection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将关联到数据库的 <code>ResultSet</code> 结果集转化为无关联的 <code>CachedRowSet</code> 缓存集。
     * 提示: 由于 <code>CachedRowSet</code> 实现了 <code>ResultSet</code>, 所以类型定义无需改变。
     *
     * @param rs 欲转化的结果集
     * @return 转化的缓存集
     */
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

    /**
     * 生成查询 “选择” <code>SELECT * FROM [table]</code>
     *
     * @param tableName 数据表名
     * @return 构造好的语句
     */
    public static String getSelectCmd(String tableName) {
        return getSelectCmd(tableName, "*");
    }

    /**
     * 生成查询 “选择” <code>SELECT [columns] FROM [table]</code>
     *
     * @param tableName 数据表名
     * @param columns   所有数据列
     * @return 构造好的语句
     */
    public static String getSelectCmd(String tableName, String... columns) {
        String baseCmd = "SELECT <COLS> FROM <TBNAME> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        StringBuilder cols = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String delimiter = i == columns.length - 1 ? "" : ",";
            cols.append(columns[i]).append(delimiter);
        }
        cmd = cmd.replace("<COLS>", cols);
        return cmd;
    }

    /**
     * 生成占位符查询 “插入” <code>INSERT INTO [table] ([columns]) VALUES (?,?...)</code>
     *
     * @param tableName 数据表名
     * @param columns   欲插入的列
     * @return 包含占位符的语句
     */
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

    /**
     * 生成占位符查询 “插入” <code>INSERT IGNORE INTO [table] ([columns]) VALUES (?,?...)</code>
     * <code>IGNORE</code>会无视错误。
     *
     * @param tableName 数据表名
     * @param columns   欲插入的列
     * @return 包含占位符的语句
     */
    public static String getInsertIgnoredCmd(String tableName, String... columns) {
        String baseCmd = "INSERT IGNORE INTO <TBNAME> <COLS> VALUES (<VALS>) ";
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

    /**
     * 生成占位符查询 “更新” <code>UPDATE [table] SET [changedColumns=?,...] </code>
     *
     * @param tableName      数据表名
     * @param changedColumns 欲修改的列
     * @return 包含占位符的语句
     */
    public static String getUpdateCmd(String tableName, String... changedColumns) {
        String baseCmd = "UPDATE <TBNAME> SET <COLS> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        StringBuilder cols = new StringBuilder();
        for (int i = 0; i < changedColumns.length; i++) {
            String delimiter = i == changedColumns.length - 1 ? "=?" : "=?,";
            cols.append(changedColumns[i]).append(delimiter);
        }
        cmd = cmd.replace("<COLS>", cols);
        return cmd;
    }

    /**
     * 生成查询 “删除” <code>DELETE FROM [table] </code>
     *
     * @param tableName 数据表名
     * @return 查询语句
     */
    public static String getDeleteCmd(String tableName) {
        String baseCmd = "DELETE FROM <TBNAME> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        return cmd;
    }

    /**
     * 生成查询 “计数” <code>SELECT COUNT(*) FROM [table] </code>
     *
     * @param tableName 数据表名
     * @return 查询语句
     */
    public static String getCountCmd(String tableName) {
        String baseCmd = "SELECT COUNT(1) FROM <TBNAME> ";
        String cmd = baseCmd.replace("<TBNAME>", tableName);
        return cmd;
    }

    /**
     * 生成 "WHERE" 限定语句。此方法用于拼接。
     *
     * @param limits 所有限定
     * @return 查询语句
     */
    public static String where(String limits) {
        return " WHERE " + limits + " ";
    }

    /**
     * 生成 "LIMIT" 限定语句。此方法用于拼接。
     *
     * @param count 限制行数
     * @return 查询语句
     */
    public static String limit(int count) {
        return " LIMIT " + count;
    }

    /**
     * 生成 "LIMIT" 限定语句。此方法用于拼接。
     *
     * @param from  从第几个结果开始
     * @param count 一共查询几个结果
     * @return 查询语句
     */
    public static String limit(int from, int count) {
        return " LIMIT " + from + "," + count;
    }

    /**
     * CAST表达式，CAST(列名 as 目标值类型)
     * 推荐使用SQLType内的目标值类型。
     *
     * @param columnName the column name
     * @param type       the type
     * @return the string
     */
    public static String cast(String columnName, String type) {
        return " CAST(" + columnName + " as " + type + ") ";
    }

    public static String like(String columnName, String value) {
        return " " + columnName + " LIKE " + "'" + value + "'";
    }

    /**
     * 从配置文件读取信息，并初始化数据连接，然后返回数据库代理类。
     * 配置文件需要包含以下字段：
     * <ul>
     *     <li><code>user</code>   => 用户名</li>
     *     <li><code>pass</code>   => 密码</li>
     *     <li><code>dbname</code> => 数据库名</li>
     *     <li><code>host</code>   => 主机地址</li>
     *     <li><code>port</code>   => 端口号</li>
     * </ul>
     *
     * @param path 配置文件路径
     * @return 数据库代理类
     */
    public static Db connectFromProperties(String path) {
        Properties properties = PropUtils.loadProperties(path);
        if (properties == null) return null;
        Db db = new Db(properties.getProperty("user"), properties.getProperty("pass"), properties.getProperty("dbname"), properties.getProperty("host"), Converter.str2int(properties.getProperty("port"), 3306));
        lastDBConnection = db.getConnection();
        return db;
    }

    /**
     * 从配置文件读取信息，并初始化数据连接，然后返回数据库代理类。
     * 配置文件需要包含以下字段：
     * <ul>
     *     <li><code>user</code>   => 用户名</li>
     *     <li><code>pass</code>   => 密码</li>
     *     <li><code>dbname</code> => 数据库名</li>
     *     <li><code>host</code>   => 主机地址</li>
     *     <li><code>port</code>   => 端口号</li>
     * </ul>
     *
     * @param path   配置文件路径
     * @param loader 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 数据库代理类
     */
    public static Db connectFromPropertiesInResource(String path, ClassLoader loader) {
        Properties properties = PropUtils.loadPropertiesAsClassResource(path, loader);
//        if (properties == null) return null;
        Db db = new Db(properties.getProperty("user"), properties.getProperty("pass"), properties.getProperty("dbname"), properties.getProperty("host"), Converter.str2int(properties.getProperty("port"), 3306));
        lastDBConnection = db.getConnection();
        return db;
    }

    /**
     * 从配置文件读取信息，并初始化数据连接，然后返回数据库代理类。
     * 配置文件需要包含以下字段：
     * <ul>
     *     <li><code>user</code>   => 用户名</li>
     *     <li><code>pass</code>   => 密码</li>
     *     <li><code>dbname</code> => 数据库名</li>
     *     <li><code>host</code>   => 主机地址</li>
     *     <li><code>port</code>   => 端口号</li>
     * </ul>
     *
     * @param path  配置文件路径
     * @param clazz 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 数据库代理类
     */
    public static Db connectFromPropertiesInResource(String path, Class<?> clazz) {
        Properties properties = PropUtils.loadPropertiesAsClassResource(path, clazz);
//        if (properties == null) return null;
        Db db = new Db(properties.getProperty("user"), properties.getProperty("pass"), properties.getProperty("dbname"), properties.getProperty("host"), Converter.str2int(properties.getProperty("port"), 3306));
        lastDBConnection = db.getConnection();
        return db;
    }

    /**
     * 获取最后一次的数据库连接
     *
     * @return Connection
     */
    public static Connection getLastConnection() {
        return lastDBConnection;
    }

    /**
     * 转换Java Date类到SQL Date类
     *
     * @param date java.util.Date
     * @return java.sql.Date
     */
    public static Date toSQLDate(java.util.Date date) {
        return new Date(date.getTime());
    }

    /**
     * 获得结果集中的结果数
     *
     * @param rs 结果集
     * @return the int
     */
    public static int getCount(ResultSet rs) {
        return getCount(rs, false);
    }

    /**
     * 获得结果集中的结果数
     *
     * @param rs           结果集
     * @param resetPointer 重置到初始位置，否则重置到传入时的位置
     * @return the int
     */
    public static int getCount(ResultSet rs, boolean resetPointer) {
        try {
            int currentRow = rs.getRow();
            int resultRow = 0;
            while (rs.next()) resultRow++;
            //rs.last();
//            int resultRow = rs.getRow();
            if (resetPointer) {
                rs.beforeFirst();
            } else {
                rs.absolute(currentRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

