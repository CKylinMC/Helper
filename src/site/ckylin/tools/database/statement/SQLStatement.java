/**
 * @Package: site.ckylin.tools.database.statement
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-06 10:48
 */
package site.ckylin.tools.database.statement;

import site.ckylin.tools.database.DbHelper;

/**
 * The type Sql statement.
 */
public class SQLStatement {

    private String builtStatement;

    private static SQLStatement instance;

    /**
     * Start sql statement.
     *
     * @return the sql statement
     */
    public static SQLStatement start () {
        if (SQLStatement.instance == null) {
            SQLStatement.instance = new SQLStatement();
        }
        instance.reset();
        return instance;
    }

    /**
     * Instantiates a new Sql statement.
     */
    public SQLStatement () {
        reset();
    }

    /**
     * Reset sql statement.
     *
     * @return the sql statement
     */
    public SQLStatement reset () {
        builtStatement = "";
        return this;
    }

    /**
     * As select cmd sql statement.
     *
     * @param tableName the table name
     * @return the sql statement
     */
    public SQLStatement asSelectCmd (String tableName) {
        builtStatement = DbHelper.getSelectCmd(tableName);
        return this;
    }

    /**
     * As select cmd sql statement.
     *
     * @param tableName the table name
     * @param columns   the columns
     * @return the sql statement
     */
    public SQLStatement asSelectCmd (String tableName, String... columns) {
        builtStatement = DbHelper.getSelectCmd(tableName, columns);
        return this;
    }

    /**
     * As inseret cmd sql statement.
     *
     * @param tableName the table name
     * @param columns   the columns
     * @return the sql statement
     */
    public SQLStatement asInseretCmd (String tableName, String... columns) {
        builtStatement = DbHelper.getInsertCmd(tableName, columns);
        return this;
    }

    /**
     * As insert ignore cmd sql statement.
     *
     * @param tableName the table name
     * @param columns   the columns
     * @return the sql statement
     */
    public SQLStatement asInsertIgnoreCmd (String tableName, String... columns) {
        builtStatement = DbHelper.getInsertIgnoredCmd(tableName, columns);
        return this;
    }

    /**
     * As update cmd sql statement.
     *
     * @param tableName      the table name
     * @param changedColumns the changed columns
     * @return the sql statement
     */
    public SQLStatement asUpdateCmd (String tableName, String... changedColumns) {
        builtStatement = DbHelper.getUpdateCmd(tableName, changedColumns);
        return this;
    }

    /**
     * As count cmd sql statement.
     *
     * @param tableName the table name
     * @return the sql statement
     */
    public SQLStatement asCountCmd (String tableName) {
        builtStatement = DbHelper.getCountCmd(tableName);
        return this;
    }

    /**
     * As delete cmd sql statement.
     *
     * @param tableName the table name
     * @return the sql statement
     */
    public SQLStatement asDeleteCmd (String tableName) {
        builtStatement = DbHelper.getDeleteCmd(tableName);
        return this;
    }

    /**
     * Where sql statement.
     *
     * @param limits the limits
     * @return the sql statement
     */
    public SQLStatement where (String limits) {
        builtStatement += DbHelper.where(limits);
        return this;
    }

    /**
     * Limit sql statement.
     *
     * @param count the count
     * @return the sql statement
     */
    public SQLStatement limit (int count) {
        builtStatement += DbHelper.limit(count);
        return this;
    }

    /**
     * Limit sql statement.
     *
     * @param from  the from
     * @param count the count
     * @return the sql statement
     */
    public SQLStatement limit(int from, int count) {
        builtStatement += DbHelper.limit(from, count);
        return this;
    }

    /**
     * Order by sql statement.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement orderBy (String columnName) {
        return orderBy(columnName, false);
    }

    /**
     * Order by sql statement.
     *
     * @param columnName the column name
     * @param isDESC     the is desc
     * @return the sql statement
     */
    public SQLStatement orderBy(String columnName, boolean isDESC) {
        String desc = isDESC ? " desc" : "";
        builtStatement += " ORDER BY " + columnName + desc;
        return this;
    }

    /**
     * And sql statement.
     *
     * @return the sql statement
     */
    public SQLStatement and() {
        builtStatement += " AND";
        return this;
    }

    /**
     * Or sql statement.
     *
     * @return the sql statement
     */
    public SQLStatement or() {
        builtStatement += " OR";
        return this;
    }

    /**
     * Where sql statement.
     *
     * @return the sql statement
     */
    public SQLStatement where() {
        builtStatement += " WHERE";
        return this;
    }

    /**
     * Not sql statement.
     *
     * @return the sql statement
     */
    public SQLStatement not() {
        builtStatement += " NOT";
        return this;
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, double value) {
        return equal(columnName, Double.toString(value), false);
    }

    /**
     * Equals sql statement with placeholder '?'.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement equal(String columnName) {
        return equal(columnName, "?", false);
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, float value) {
        return equal(columnName, Float.toString(value), false);
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, long value) {
        return equal(columnName, Long.toString(value), false);
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, int value) {
        return equal(columnName, Integer.toString(value), false);
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, String value) {
        return equal(columnName, value, true);
    }

    /**
     * Equals sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @param quoted     the quoted
     * @return the sql statement
     */
    public SQLStatement equal(String columnName, String value, boolean quoted) {
        if (quoted) value = "'" + value + "'";
        builtStatement += " `" + columnName + "`=" + value;
        return this;
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, double value) {
        return equalOrLess(columnName, Double.toString(value), false);
    }

    /**
     * Equals or less sql statement with placeholder '?'.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName) {
        return equalOrLess(columnName, "?", false);
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, float value) {
        return equalOrLess(columnName, Float.toString(value), false);
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, long value) {
        return equalOrLess(columnName, Long.toString(value), false);
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, int value) {
        return equalOrLess(columnName, Integer.toString(value), false);
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, String value) {
        return equalOrLess(columnName, value, true);
    }

    /**
     * Equals or less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @param quoted     the quoted
     * @return the sql statement
     */
    public SQLStatement equalOrLess(String columnName, String value, boolean quoted) {
        if (quoted) value = "'" + value + "'";
        builtStatement += " `" + columnName + "`<=" + value;
        return this;
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, double value) {
        return equalOrLarge(columnName, Double.toString(value), false);
    }

    /**
     * Equals or large sql statement with placeholder '?'.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName) {
        return equalOrLarge(columnName, "?", false);
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, float value) {
        return equalOrLarge(columnName, Float.toString(value), false);
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, long value) {
        return equalOrLarge(columnName, Long.toString(value), false);
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, int value) {
        return equalOrLarge(columnName, Integer.toString(value), false);
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, String value) {
        return equalOrLarge(columnName, value, true);
    }

    /**
     * Equals or large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @param quoted     the quoted
     * @return the sql statement
     */
    public SQLStatement equalOrLarge(String columnName, String value, boolean quoted) {
        if (quoted) value = "'" + value + "'";
        builtStatement += " `" + columnName + "`>=" + value;
        return this;
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement less(String columnName, double value) {
        return less(columnName, Double.toString(value), false);
    }

    /**
     * Less sql statement with placeholder '?'.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement less(String columnName) {
        return less(columnName, "?", false);
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement less(String columnName, float value) {
        return less(columnName, Float.toString(value), false);
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement less(String columnName, long value) {
        return less(columnName, Long.toString(value), false);
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement less(String columnName, int value) {
        return less(columnName, Integer.toString(value), false);
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement less(String columnName, String value) {
        return less(columnName, value, true);
    }

    /**
     * Less sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @param quoted     the quoted
     * @return the sql statement
     */
    public SQLStatement less(String columnName, String value, boolean quoted) {
        if (quoted) value = "'" + value + "'";
        builtStatement += " `" + columnName + "`<" + value;
        return this;
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement large(String columnName, double value) {
        return large(columnName, Double.toString(value), false);
    }

    /**
     * Large sql statement with placeholder '?'.
     *
     * @param columnName the column name
     * @return the sql statement
     */
    public SQLStatement large(String columnName) {
        return large(columnName, "?", false);
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement large(String columnName, float value) {
        return large(columnName, Float.toString(value), false);
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement large(String columnName, long value) {
        return large(columnName, Long.toString(value), false);
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement large(String columnName, int value) {
        return large(columnName, Integer.toString(value), false);
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the sql statement
     */
    public SQLStatement large(String columnName, String value) {
        return large(columnName, value, true);
    }

    /**
     * Large sql statement.
     *
     * @param columnName the column name
     * @param value      the value
     * @param quoted     the quoted
     * @return the sql statement
     */
    public SQLStatement large(String columnName, String value, boolean quoted) {
        if (quoted) value = "'" + value + "'";
        builtStatement += " `" + columnName + "`>" + value;
        return this;
    }

    /**
     * Build string.
     *
     * @return the string
     */
    public String build() {
        return builtStatement;
    }

    @Override
    public String toString () {
        return build();
    }
}
