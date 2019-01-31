package com.sagar.qbar.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.result.ParsedResultType;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 22:04
 */

public class Converter {

    @TypeConverter
    public static Integer fromBarcodeFormatToOrdinal(BarcodeFormat barcodeFormat) {
        return barcodeFormat.ordinal();
    }

    @TypeConverter
    public static BarcodeFormat fromOrdinalToBarcodeFormat(Integer ordinal) {

        for (BarcodeFormat type : BarcodeFormat.values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }

        return BarcodeFormat.QR_CODE;
    }

    @TypeConverter
    public static Integer fromParsedResultTypeToOrdinal(ParsedResultType resultType) {
        return resultType.ordinal();
    }

    @TypeConverter
    public static ParsedResultType fromOrdinalToParsedResultType(Integer ordinal) {

        for (ParsedResultType type : ParsedResultType.values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }

        return ParsedResultType.TEXT;
    }

}