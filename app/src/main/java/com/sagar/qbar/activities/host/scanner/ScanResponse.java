package com.sagar.qbar.activities.host.scanner;

import com.google.zxing.client.result.ParsedResultType;

/**
 * Created by SAGAR MAHOBIA on 29-Jan-19. at 18:42
 */
public class ScanResponse {

    private long id;
    private ParsedResultType type;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParsedResultType getType() {
        return type;
    }

    public void setType(ParsedResultType type) {
        this.type = type;
    }
}
