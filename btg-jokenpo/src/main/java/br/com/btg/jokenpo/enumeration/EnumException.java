package br.com.btg.jokenpo.enumeration;

public enum EnumException {

    GENERIC_ERROR("ERROR-0001", "JOKENPO", "GENERIC ERROR", "GENERIC ERROR", "Erro desconhecido");

    private String code;
    private String origin;
    private String type;
    private String subType;
    private String message;

    EnumException(){
    }

    EnumException(String code, String origin, String type, String subType, String message) {
        this.code = code;
        this.origin = origin;
        this.type = type;
        this.subType = subType;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getOrigin() {
        return origin;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EnumException getEnumExceptionByCode(String code){
        for(EnumException element : EnumException.values()){
            if(code.equals(element.getCode())){
                return element;
            }
        }
        return EnumException.GENERIC_ERROR;
    }
}
