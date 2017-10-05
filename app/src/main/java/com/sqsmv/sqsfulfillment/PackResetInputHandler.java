package com.sqsmv.sqsfulfillment;

import android.content.Context;

import com.sqsmv.sqsfulfillment.database.pack.PackDataAccess;
import com.sqsmv.sqsfulfillment.database.pack.PackRecord;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanDataAccess;
import com.sqsmv.sqsfulfillment.database.packresetscan.PackResetScanRecord;

import org.cory.libraries.MoreDateFunctions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackResetInputHandler
{
    private static Pattern packPattern;

    private PackDataAccess packDataAccess;
    private PackResetScanDataAccess packResetScanDataAccess;

    private PackRecord currentPackRecord;

    public PackResetInputHandler(Context activityContext, PackRecord packRecord)
    {
        packPattern = Pattern.compile(activityContext.getString(R.string.pack_regex));

        currentPackRecord = packRecord;

        packDataAccess = new PackDataAccess(activityContext);
        packResetScanDataAccess = new PackResetScanDataAccess(activityContext);
    }

    public void openDBs()
    {
        packDataAccess.read();
        packResetScanDataAccess.open();
    }

    public void closeDBs()
    {
        packDataAccess.close();
        packResetScanDataAccess.close();
    }

    public ScanReturn handleRegularScan(String scanData, String quantity, String scanId, String scannerInitials)
    {
        ScanReturn response = ScanReturn.SCAN_INSERTED;
        Matcher packMatcher = packPattern.matcher(scanData);

        if(packMatcher.find())
        {
            String packId = packMatcher.group(1);
            if(currentPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
            {
                if(!quantity.isEmpty() && Integer.parseInt(quantity) > 0)
                {
                    if(scannerInitials.length() > 1)
                    {
                        insertScan("", quantity, scanId, scannerInitials);
                    }
                    else
                    {
                        response = ScanReturn.NO_INITIALS;
                    }
                }
                else
                {
                    response = ScanReturn.NEEDS_QUANTITY;
                }
            }
            else
            {
                currentPackRecord.clearRecord();
                currentPackRecord.accessPackName("Pack not found. ID: " + packId);
                response = ScanReturn.PACK_NOT_FOUND;
            }
        }
        else
        {
            currentPackRecord.accessPackName("Error: Not a pack");
            response = ScanReturn.BAD_BARCODE;
        }

        return response;
    }

    public ScanReturn handleSingleScan(String scanData, String scanId, String scannerInitials)
    {
        ScanReturn response = ScanReturn.SCAN_INSERTED;
        Matcher packMatcher = packPattern.matcher(scanData);

        if(packMatcher.find())
        {
            String packId = packMatcher.group(1);
            String pullMasterId;
            if(packMatcher.group(3) == null)
            {
                pullMasterId = "";
            }
            else
            {
                pullMasterId = packMatcher.group(3);
            }
            if(currentPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
            {
                if(scannerInitials.length() > 1)
                {
                    insertScan(pullMasterId, "1", scanId, scannerInitials);
                }
                else
                {
                    response = ScanReturn.NO_INITIALS;
                }
            }
            else
            {
                currentPackRecord.clearRecord();
                currentPackRecord.accessPackName("Pack not found. ID: " + packId);
                response = ScanReturn.PACK_NOT_FOUND;
            }
        }
        else
        {
            currentPackRecord.accessPackName("Error: Not a pack");
            response = ScanReturn.BAD_BARCODE;
        }

        return response;
    }

    public ScanReturn handleManualInput(String packId, String quantity, String scanId, String scannerInitials)
    {
        ScanReturn response = ScanReturn.SCAN_INSERTED;

        if(currentPackRecord.buildWithCursor(packDataAccess.selectByPk(packId)))
        {
            if(!quantity.isEmpty() && Integer.parseInt(quantity) > 0)
            {
                if(scannerInitials.length() > 1)
                {
                    insertScan("", quantity, scanId, scannerInitials);
                }
                else
                {
                    response = ScanReturn.NO_INITIALS;
                }
            }
            else
            {
                response = ScanReturn.NEEDS_QUANTITY;
            }
        }
        else
        {
            currentPackRecord.clearRecord();
            currentPackRecord.accessPackName("Pack not found. ID: " + packId);
            response = ScanReturn.PACK_NOT_FOUND;
        }

        return response;
    }

    private void insertScan(String pullMasterId, String quantity, String scanId, String scannerInitials)
    {
        packResetScanDataAccess.insert(new PackResetScanRecord(currentPackRecord.accessPkPackId(null), quantity,
                                                                scanId, MoreDateFunctions.getTodaySlashMMDDYY(),
                                                                scannerInitials, currentPackRecord.accessPackName(null)));
        currentPackRecord.clearRecord();
    }
}
