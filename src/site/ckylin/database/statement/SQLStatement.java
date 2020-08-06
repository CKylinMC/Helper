/**
 * @Package: site.ckylin.database.statement
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-06 10:48
 */
package site.ckylin.database.statement;

import site.ckylin.database.DbHelper;

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
     * @param from the from
     * @param to   the to
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
    public SQLStatement orderBy (String columnName, boolean isDESC) {
        String desc = isDESC ? " desc" : "";
        builtStatement += " ORDER BY " + columnName + desc;
        return this;
    }

    /**
     * Build string.
     *
     * @return the string
     */
    public String build () {
        return builtStatement;
    }

    @Override
    public String toString () {
        return build();
    }
}
