package com.sqsmv.sqsfulfillment.database;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.sqsmv.sqsfulfillment.StartupActivity;

public class InvoiceTest extends ActivityUnitTestCase<StartupActivity>
{
    InvoiceDataAccess da;

    public InvoiceTest()
    {
        super(StartupActivity.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        startActivity(new Intent(getInstrumentation().getTargetContext(), StartupActivity.class), null, null);
        da = new InvoiceDataAccess(getActivity());
    }

    public void test_insert_creates_an_id()
    {

    }
/*
    public void testInsert()
    {
        DataRecord record1 = new InvoiceRecord("23456789", "12345", "150", "9", "10", "TestBatch", "2/28/2015", "1 of 5", "1ab");

        da.open();
        da.insert(record1);

        InvoiceRecord record2 = new InvoiceRecord();
        assertTrue(record2.buildWithCursor(da.selectByPk("50")));
        assertEquals("50", record2.accessPkInvoiceId(null));
        assertEquals("200", record2.accessFkPackId(null));
        assertEquals("150", record2.accessFkShipToId(null));
        assertEquals("9", record2.accessFkShipRequestId(null));
        assertEquals("10", record2.accessFkBatchId(null));
        assertEquals("TestBatch", record2.accessBatchName(null));
        assertEquals("2/28/2015", record2.accessDocDate(null));
        assertEquals("1 of 5", record2.accessBoxNum(null));
        assertEquals("", record2.accessIsScanned(null));
        assertEquals("1ab", record2.accessSha(null));
        da.close();
    }
*/
    /*
    public void testBatchInsert()
    {
        invoiceDS.open();
        invoiceR
        invoiceDS.close();
    }
    */
    @Override
    protected void tearDown()
    {
    }
}
