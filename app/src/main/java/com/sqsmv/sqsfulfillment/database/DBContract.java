package com.sqsmv.sqsfulfillment.database;

//Database does not do any queries, just specifies column names and information related to them

public interface DBContract
{
    String getTableName();

    String getTableCreateString();

    String getTableDropString();

    String getPrimaryKeyName();

    String[] getColumnNames();
}
