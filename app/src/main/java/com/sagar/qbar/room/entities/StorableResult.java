package com.sagar.qbar.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.result.ParsedResultType;


/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 19:21
 */
@Entity(tableName = "results")
public class StorableResult {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "barcode_format")
    private BarcodeFormat barcodeFormat;

    @ColumnInfo(name = "parsed_result_type")
    private ParsedResultType parsedResultType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public BarcodeFormat getBarcodeFormat() {
        return barcodeFormat;
    }

    public void setBarcodeFormat(BarcodeFormat barcodeFormat) {
        this.barcodeFormat = barcodeFormat;
    }

    public ParsedResultType getParsedResultType() {
        return parsedResultType;
    }

    public void setParsedResultType(ParsedResultType parsedResultType) {
        this.parsedResultType = parsedResultType;
    }
}
