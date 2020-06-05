package br.com.btg.jokenpo.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

public class FormatResponse<T> {

    private TimeResponse meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public FormatResponse() {
    }

    public FormatResponse(T data) {
        this.meta = new TimeResponse(new Timestamp(System.currentTimeMillis()));
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TimeResponse getMeta() {
        return meta;
    }

    public void setMeta (TimeResponse meta) {
        this.meta = meta;
    }

}
