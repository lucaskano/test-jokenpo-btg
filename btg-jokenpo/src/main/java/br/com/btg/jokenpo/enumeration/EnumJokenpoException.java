package br.com.btg.jokenpo.enumeration;

import org.springframework.stereotype.Component;

@Component
public enum EnumJokenpoException {

    GENERIC_ERROR("ERROR-0001", "JOKENPO", "GENERIC ERROR", "GENERIC ERROR", "Erro desconhecido");

    private String code;
    private String origin;
    private String type;
    private String subType;
    private String detail;

    EnumJokenpoException(){
    }

    EnumJokenpoException(String code, String origin, String type, String subType, String detail) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
