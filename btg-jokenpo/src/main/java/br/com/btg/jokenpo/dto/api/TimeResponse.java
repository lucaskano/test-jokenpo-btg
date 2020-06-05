package br.com.btg.jokenpo.dto.api;

import java.sql.Timestamp;

public class TimeResponse {

    private Timestamp timestamp;

    public TimeResponse() {}

    public TimeResponse(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
