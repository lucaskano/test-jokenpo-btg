package br.com.btg.jokenpo.dto.api;

import java.sql.Timestamp;

public class FormatResponse {

    private Timestamp timestamp;

    public FormatResponse() {}

    public FormatResponse(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
