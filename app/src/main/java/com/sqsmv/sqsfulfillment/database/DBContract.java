package com.sqsmv.sqsfulfillment.database;

//Database does not do any queries, just specifies column names and information related to them

public interface DBContract
{
    public String getTableName();

    public String getTableCreateString();

    public String getTableDropString();

    public String getPrimaryKeyName();

    public String[] getColumnNames();
}
