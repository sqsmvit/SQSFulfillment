package com.sqsmv.sqsfulfillment;

public enum FulfillmentScanReturn
{
    SCAN_INSERTED,
    DISPLAY_SCAN,
    NO_ID,
    BAD_BARCODE,
    BAD_INVOICE,
    PACK_NOT_FOUND,
    PACK_NEEDS_VALIDATION,
    DOUBLE_PACK_SCANNED,
    WRONG_PACK_SCANNED,
    CONFIG_NEEDS_VALIDATION
}
