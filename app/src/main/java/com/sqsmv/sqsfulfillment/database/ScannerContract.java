package com.sqsmv.sqsfulfillment.database;

public class ScannerContract implements XMLFileContract
{
	private static final String TABLE_NAME = "Scanner";
    private static final String XML_FILE_NAME = "scanner.xml";
	protected static final String COLUMN_NAME_PKSCANNERID = "pkscannerid";
    protected static final String COLUMN_NAME_FULLNAME = "fullname";
    protected static final String COLUMN_NAME_ISMANAGEMENT = "ismanagement";
    protected static final String COLUMN_NAME_ISTEMP = "istemp";

    //Matches order of SQLite Table and XML
    protected static final String[] SCANNER_COLUMNS =
            {
                    COLUMN_NAME_PKSCANNERID,
                    COLUMN_NAME_FULLNAME,
                    COLUMN_NAME_ISMANAGEMENT,
                    COLUMN_NAME_ISTEMP,
                    COLUMN_NAME_SHA
            };

    protected static final String CREATE_TABLE_SCANNER = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_NAME_PKSCANNERID + " TEXT PRIMARY KEY, " +
            COLUMN_NAME_FULLNAME + " TEXT, " +
            COLUMN_NAME_ISMANAGEMENT + " TEXT, " +
            COLUMN_NAME_ISTEMP + " TEXT, " +
            COLUMN_NAME_SHA + " TEXT);";

    protected static final String DROP_TABLE_SCANNER = "DROP TABLE IF EXISTS " + TABLE_NAME;

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getTableCreateString()
    {
        return CREATE_TABLE_SCANNER;
    }

    @Override
    public String getTableDropString()
    {
        return DROP_TABLE_SCANNER;
    }

    @Override
    public String getPrimaryKeyName()
    {
        return COLUMN_NAME_PKSCANNERID;
    }

    @Override
    public String[] getColumnNames()
    {
        return SCANNER_COLUMNS;
    }

    @Override
    public String getXMLFileName()
    {
        return XML_FILE_NAME;
    }
}
