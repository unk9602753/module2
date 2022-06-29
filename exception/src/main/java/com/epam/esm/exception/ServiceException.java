package com.epam.esm.exception;

public class ServiceException extends Exception {
    private long id;
    private String concreteMessage;

    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode, long id){
        this.errorCode=errorCode;
        this.id = id;
    }

    public ServiceException(ErrorCode errorCode, String concreteMessage){
        this.errorCode=errorCode;
        this.concreteMessage = concreteMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getId() {
        if (id != 0) {
            return String.valueOf(id);
        }
        return "";
    }

    public String getConcreteMessage() {
        return concreteMessage != null ? concreteMessage : "";
    }
}
