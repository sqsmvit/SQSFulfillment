package com.sqsmv.sqsfulfillment.database;

import junit.framework.TestCase;

public class DataRecordTest extends TestCase
{
    public void test_Polymorphism()
    {
        PackagingRecord packagingRecord = new PackagingRecord();
        DataRecord dataRecord = packagingRecord;
        dataRecord.setValueByKey("pkmasnum", "123");
        assertEquals("123", packagingRecord.accessPkMasnum(null));
        assertEquals("123", dataRecord.getValueByKey("pkmasnum"));
    }

    public void test_NewCopy()
    {
        PackagingRecord packagingRecord = new PackagingRecord();
        packagingRecord.accessPkMasnum("321");
        DataRecord dataRecord = packagingRecord.newCopy();
        assertEquals("321", dataRecord.getValueByKey("pkmasnum"));
        assertEquals("321", packagingRecord.accessPkMasnum(null));
        packagingRecord.accessPkMasnum("323");
        assertEquals("321", dataRecord.getValueByKey("pkmasnum"));
        assertEquals("323", packagingRecord.accessPkMasnum(null));
    }
}
