package com.nyu.database.index.hash;

public class Info {
    private String tableName;
    private String columnName;

    public Info(String t, String c) {
        tableName = t;
        columnName = c;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
