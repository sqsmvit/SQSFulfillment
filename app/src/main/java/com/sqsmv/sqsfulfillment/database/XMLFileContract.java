package com.sqsmv.sqsfulfillment.database;

public interface XMLFileContract extends DBContract
{
    public static final String COLUMN_NAME_SHA = "sha";

    public String getXMLFileName();
}
