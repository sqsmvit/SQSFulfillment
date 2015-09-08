package com.sqsmv.sqsfulfillment.database.pack;

import com.sqsmv.sqsfulfillment.database.XMLFileContract;

public class PackContract implements XMLFileContract
{
	private static final String TABLE_NAME = "Pack";
    private static final String XML_FILE_NAME = "pack.xml";
	protected static final String COLUMN_NAME_PKPACKID = "pkpackid";
    protected static final String COLUMN_NAME_FKLENSID = "fklensid";
    protected static final String COLUMN_NAME_FKBOXMASNUM = "fkboxmasnum";
    protected static final String COLUMN_NAME_PACKNAME = "packname";
    protected static final String COLUMN_NAME_PRODQUANTITY = "prodquantity";
    protected static final String COLUMN_NAME_NUMPACKLINES = "numpacklines";
    protected static final String COLUMN_NAME_ISDOUBLE = "isdouble";
    protected static final String COLUMN_NAME_ISVALID = "isvalid";
    protected static final String COLUMN_NAME_COUNTONHAND = "countonhand";
    protected static final String COLUMN_NAME_PULLQUEUED = "pullqueued";

    //Matches order of SQLite Table and XML
    protected static final String[] PACK_COLUMNS =
            {
                    COLUMN_NAME_PKPACKID,
                    COLUMN_NAME_FKLENSID,
                    COLUMN_NAME_FKBOXMASNUM,
                    COLUMN_NAME_PACKNAME,
                    COLUMN_NAME_PRODQUANTITY,
                    COLUMN_NAME_NUMPACKLINES,
                    COLUMN_NAME_ISDOUBLE,
                    COLUMN_NAME_ISVALID,
                    COLUMN_NAME_COUNTONHAND,
                    COLUMN_NAME_PULLQUEUED,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_PACK = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKPACKID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FKLENSID + " TEXT, " +
            COLUMN_NAME_FKBOXMASNUM + " TEXT, " +
            COLUMN_NAME_PACKNAME + " TEXT, " +
            COLUMN_NAME_PRODQUANTITY + " TEXT, " +
            COLUMN_NAME_NUMPACKLINES + " TEXT, " +
            COLUMN_NAME_ISDOUBLE + " TEXT, " +
            COLUMN_NAME_ISVALID + " TEXT, " +
            COLUMN_NAME_COUNTONHAND + " TEXT, " +
            COLUMN_NAME_PULLQUEUED + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_PACK = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACK;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACK;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKPACKID;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACK_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
