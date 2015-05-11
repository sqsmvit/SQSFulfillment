package com.sqsmv.sqsfulfillment.database.unimplemented;

import com.sqsmv.sqsfulfillment.database.XMLFileContract;

public class PackRecreateRulesContract implements XMLFileContract
{
    private static final String TABLE_NAME = "PackRecreateRules";
    private static final String XML_FILE_NAME = "packrecreaterules.xml";
    protected static final String COLUMN_NAME_PKRULESID = "pkrulesid";
    protected static final String COLUMN_NAME_FKLENSID = "fklensid";
    protected static final String COLUMN_NAME_ALLOWEDFKLENSID = "allowedfklensid";

    //Matches order of SQLite Table and XML
    protected static final String[] PACKRECREATERULES_COLUMNS =
            {
                    COLUMN_NAME_PKRULESID,
                    COLUMN_NAME_FKLENSID,
                    COLUMN_NAME_ALLOWEDFKLENSID,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_PACKRECREATERULES = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKRULESID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FKLENSID + " TEXT, " +
            COLUMN_NAME_ALLOWEDFKLENSID + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_PACKRECREATERULES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACKRECREATERULES;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACKRECREATERULES;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKRULESID;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACKRECREATERULES_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
