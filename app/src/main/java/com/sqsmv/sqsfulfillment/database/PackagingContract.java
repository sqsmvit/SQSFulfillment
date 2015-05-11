package com.sqsmv.sqsfulfillment.database;

public class PackagingContract implements XMLFileContract
{
	private static final String TABLE_NAME = "Packaging";
    private static final String XML_FILE_NAME = "packaging.xml";
	protected static final String COLUMN_NAME_PKMASNUM = "pkmasnum";
    protected static final String COLUMN_NAME_PACKAGINGNAME = "packagingname";

    //Matches order of SQLite Table and XML
    protected static final String[] PACKAGING_COLUMNS =
            {
                    COLUMN_NAME_PKMASNUM,
                    COLUMN_NAME_PACKAGINGNAME,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_PACKAGING = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKMASNUM + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_PACKAGINGNAME + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_PACKAGING = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_PACKAGING;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_PACKAGING;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKMASNUM;
    }

    @Override
    public String[] getColumnNames()
    {
        return PACKAGING_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
