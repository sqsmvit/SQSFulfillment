package com.sqsmv.sqsfulfillment.database;

public class ConfigContract implements XMLFileContract
{
    private static final String TABLE_NAME = "Config";
    private static final String XML_FILE_NAME = "config.xml";
    protected static final String COLUMN_NAME_PKCONFIGID = "pkconfigid";
    protected static final String COLUMN_NAME_FKDOUBLEPACKID = "fkdoublepackid";
    protected static final String COLUMN_NAME_CONFIGNUM = "confignum";
    protected static final String COLUMN_NAME_FKCONFIGPACKIDS = "fkconfigpackids";
    protected static final String COLUMN_NAME_ISVALID = "isvalid";

    protected static final String[] CONFIG_COLUMNS =
            {
                    COLUMN_NAME_PKCONFIGID,
                    COLUMN_NAME_FKDOUBLEPACKID,
                    COLUMN_NAME_CONFIGNUM,
                    COLUMN_NAME_FKCONFIGPACKIDS,
                    COLUMN_NAME_ISVALID,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_CONFIG = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKCONFIGID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FKDOUBLEPACKID + " TEXT, " +
            COLUMN_NAME_CONFIGNUM + " TEXT, " +
            COLUMN_NAME_FKCONFIGPACKIDS + " TEXT, " +
            COLUMN_NAME_ISVALID + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_CONFIG = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_CONFIG;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_CONFIG;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKCONFIGID;
    }

    @Override
    public String[] getColumnNames()
    {
        return CONFIG_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
