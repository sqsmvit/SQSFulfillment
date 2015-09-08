package com.sqsmv.sqsfulfillment.unimplemented;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.DataRecord;
import com.sqsmv.sqsfulfillment.database.pack.PackDataAccess;
import com.sqsmv.sqsfulfillment.database.pack.PackRecord;
import com.sqsmv.sqsfulfillment.database.unimplemented.PackRecreateScanDataAccess;
import com.sqsmv.sqsfulfillment.database.unimplemented.PackRecreateScanRecord;

import org.cory.libraries.MoreDateFunctions;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Assumption: When this class is being used, packs have already been researched and are in the process of being destroyed and recreated

public class PackRecreateScanHandler
{
    private PackDataAccess packDataAccess;
    private PackRecreateScanDataAccess packRecreateScanDataAccess;

    private ArrayList<DataRecord> recreateScanBatch;

    String scannerInitials, currentOperation; //Operations: +, -
    int destroyQuantity, createQuantity;

    public PackRecreateScanHandler(Context activityContext, String initials, String initialOperation, ArrayList<DataRecord> batch)
    {
        recreateScanBatch = batch;
        packDataAccess = new PackDataAccess(activityContext);
        packRecreateScanDataAccess = new PackRecreateScanDataAccess(activityContext);

        scannerInitials = initials;
        currentOperation = initialOperation;
        destroyQuantity = 0;
        createQuantity = 0;
    }

    public void openDBs()
    {
        packDataAccess.open();
        packRecreateScanDataAccess.open();
    }

    public void closeDBs()
    {
        packDataAccess.close();
        packRecreateScanDataAccess.close();
    }

    public String accessScannerInitials(String initials)
    {
        if(initials != null)
            scannerInitials = initials;
        return scannerInitials;
    }

    public String accessCurrentOperation(String operation)
    {
        if(operation != null)
            currentOperation = operation;
        return currentOperation;
    }

    public int handleScan(String scanData)
    {
        int response = 0;

        if(!accessScannerInitials(null).isEmpty() && accessScannerInitials(null).length() >= 2)
        {
            Pattern packPattern = Pattern.compile("^k(\\d{1,5})$");
            Matcher packMatcher = packPattern.matcher(scanData);

            PackRecord tempPackRecord = new PackRecord();
            // currentPackRecord.clearRecord();

            if(currentOperation.equals("+") || currentOperation.equals("-"))
            {
                if(packMatcher.find())
                {
                    String packId = packMatcher.group(1);
                    if(!tempPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
                    {
                        tempPackRecord.accessPkPackId(packId);
                        tempPackRecord.accessPackName("Pack not found. ID: " + packId);
                    }
                    PackRecreateScanRecord tempPackRecreateScanRecord = new PackRecreateScanRecord(tempPackRecord.accessPkPackId(null), currentOperation, MoreDateFunctions.getTodaySlashMMDDYY(), scannerInitials, tempPackRecord.accessPackName(null));
                    recreateScanBatch.add(tempPackRecreateScanRecord);
                }
                else
                {
                    //Bad barcode
                    response = 3;
                }
            }
            else
            {
                //Illegal operation
                response = 2;
            }
        }
        else
        {
            //No initials
            response = 1;
        }

        return response;
    }

    public void finishScans()
    {
        //packRecreateScanDataAccess.insertBatch(recreateScanBatch);
    }

    public void resetRecreation()
    {
        recreateScanBatch.clear();
        destroyQuantity = 0;
        createQuantity = 0;
    }
}
