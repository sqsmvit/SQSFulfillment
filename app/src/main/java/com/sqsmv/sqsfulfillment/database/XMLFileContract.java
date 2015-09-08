package com.sqsmv.sqsfulfillment.database;

public interface XMLFileContract extends DBContract
{
    String COLUMN_NAME_SHA = "sha";

    String getXMLFileName();
}
