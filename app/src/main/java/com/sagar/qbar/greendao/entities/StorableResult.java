package com.sagar.qbar.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by SAGAR MAHOBIA on 10-Aug-18. at 21:51
 */

@Entity(nameInDb = "results")
public class StorableResult {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

    @Property(nameInDb = "text")
    private String text;

    @Property(nameInDb = "timestamp")
    private long timestamp;

    @Property(nameInDb = "result_type")
    private int resultType;

    @Generated(hash = 634034590)
    public StorableResult(Long id, String text, long timestamp, int resultType) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.resultType = resultType;
    }

    @Generated(hash = 1269022429)
    public StorableResult() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getResultType() {
        return this.resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

}
