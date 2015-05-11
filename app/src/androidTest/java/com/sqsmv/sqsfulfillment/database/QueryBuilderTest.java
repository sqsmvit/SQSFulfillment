package com.sqsmv.sqsfulfillment.database;

import junit.framework.TestCase;

public class QueryBuilderTest extends TestCase
{
    public void testSelectBuilder()
    {
        assertEquals("SELECT * FROM Invoice WHERE pkinvoiceid = ?, sha = ?", QueryBuilder.buildSelectQuery("Invoice", new String[]{"*"}, new String[]{InvoiceContract.COLUMN_NAME_PKINVOICEID, InvoiceContract.COLUMN_NAME_SHA}));
    }
}