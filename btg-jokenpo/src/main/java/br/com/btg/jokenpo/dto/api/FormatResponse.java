package br.com.btg.jokenpo.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;

public class ApiResponse<T> {

    private FormatResponse meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.meta = new FormatResponse(new Timestamp(System.currentTimeMillis()));
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public FormatResponse getMeta() {
        return meta;
    }

    public void setMeta (FormatResponse meta) {
        this.meta = meta;
    }

}
