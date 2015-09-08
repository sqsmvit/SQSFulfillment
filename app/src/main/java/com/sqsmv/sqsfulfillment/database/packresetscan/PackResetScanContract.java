package com.sqsmv.sqsfulfillment.database.packresetscan;

import android.provider.BaseColumns;

import com.sqsmv.sqsfulfillment.database.DBContract;

public class PackResetScanContract implements DBContract, BaseColumns
{
    protected static final String TABLE_NAME = "PackResetScan";
    public static final String COLUMN_NAME_FKPACKID = "fkpackid";
    public static final String COLUMN_NAME_FKPULLMASTERID = "fkpullmasterid";
    public static final String COLUMN_NAME_QUANTITY = "quantity";
    public static final String COLUMN_NAME_SCANID = "scanid";
    public static final String COLUMN_NAME_SCANDATE = "scandate";
    public static final String COLUMN_NAME_SCANNERINITIALS = "scannerinitials";
    public static final String COLUMN_NAME_SCANNEDPACKNAME = "scannedpackname";

    //Matches order of SQLite Table
    protected static final String[] PACKRESETSCAN_COLUMNS =
            {
                    _ID,
                    COLUMN_NAME_FKPACKID,
                    COLUMN_NAME_FKPULLMASTERID,
                    COLUMN_NAME_QUANTITY,
                    COLUMN_NAME_SCANID,
                    COLUMN_NAME_SCANDATE,
                    COLUMN_NAME_SCANNERINITIALS,
                    COLUMN_NAME_SCANNEDPACKNAME
            };
    protected static final String CREATE_TABLE_PACKRESETSCAN = "CREATE TABLE " +
            TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_FKPACKID + " TEXT, " +
            COLUMN_NAME_FKPULLMASTERID + " TEXT, " +
            COLUMN_NAME_QUANTITY + " TEXT, " +
            COLUMN_NAME_SCANID + " TEXT, " +
            COLUMN_NAME_SCANDATE + " TEXT, " +
            COLUMN_NAME_SCANNERINITIALS + " TEXT, " +
            COLUMN_NAME_SCANNEDPACKNAME + " TEXT);";

    protected static final String DROP_TABLE_PACKRESETSCAN = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACKRESETSCAN;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACKRESETSCAN;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return _ID;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACKRESETSCAN_COLUMNS;
    }
}
