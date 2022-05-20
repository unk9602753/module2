package com.epam.esm.exception;

import lombok.Getter;

public class ServiceException extends RuntimeException {
    private long id;
    private String concreteMessage;
    private String message;

    public ServiceException(String message, long id) {
        this.message = message;
        this.id = id;
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, String concreteMessage) {
        this.message = message;
        this.concreteMessage = concreteMessage;
    }

    @Override
    public String getMessage() {
        return message;
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
