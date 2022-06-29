package com.epam.esm.controller;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ResponseException;
import com.epam.esm.config.Translator;
import com.epam.esm.exception.ServiceException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import static com.epam.esm.exception.ErrorCode.CODE_40004;
import static com.epam.esm.exception.ErrorCode.CODE_40007;
import static com.epam.esm.exception.ErrorCode.CODE_40009;
import static com.epam.esm.exception.ErrorCode.CODE_40011;
import static com.epam.esm.exception.ErrorCode.CODE_40014;
import static com.epam.esm.exception.ErrorCode.CODE_40017;
import static com.epam.esm.exception.ErrorCode.CODE_40040;
import static com.epam.esm.exception.ErrorCode.CODE_40041;
import static com.epam.esm.exception.ErrorCode.CODE_40046;
import static com.epam.esm.exception.ErrorCode.getCode;
import static com.epam.esm.exception.ErrorCode.getErrorCode;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, CODE_40011.getMessage(), getCode(CODE_40011), ex.getMethod()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, CODE_40014.getMessage(), getCode(CODE_40014), String.valueOf(ex.getContentType())), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, CODE_40007.getMessage(), getCode(CODE_40007), ex.getRequiredType().getName()), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(handleJacksonException(ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ArrayList<String> errors = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            errors.add(objectError.getDefaultMessage());
        }
        ErrorCode errorCode = getErrorCode(errors.get(0));
        return new ResponseEntity<>(errorBody(ex, errors.get(0), getCode(errorCode)), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, CODE_40009.getMessage(), getCode(CODE_40009)), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, ex.getMessage(), getCode(CODE_40017)), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(errorBody(ex, CODE_40009.getMessage() + 6666666, getCode(CODE_40009)), status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return new ResponseEntity<>(errorBody(ex, CODE_40009.getMessage() + 7777777, getCode(CODE_40009)), status);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException e) {
        return new ResponseEntity<>(errorBody(e, e.getErrorCode().getMessage(), getCode(e.getErrorCode()), e.getId(), e.getConcreteMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidDefinitionException(InvalidDefinitionException e) {
        return new ResponseEntity<>(errorBody(e, CODE_40004.getMessage(), getCode(CODE_40004)), HttpStatus.BAD_REQUEST);
    }

    private String getCauseInsideQuotes(String message) {
        return message.substring(message.indexOf("\"") + 1, message.lastIndexOf("\""));
    }

    private ResponseException errorBody(Throwable e, String messageCode, int code, String... args) {
        ResponseException responseException = new ResponseException();
        responseException.setCode(code);
        responseException.setMessage(String.format(Translator.toLocale(messageCode), args));
        return responseException;
    }

    private ResponseException handleJacksonException(Throwable ex) {
        Throwable cause = ex.getCause();
        ResponseException responseException = null;
        String message = "";
        String messageSecondReason = "";
        if (cause instanceof IgnoredPropertyException) {
            IgnoredPropertyException jpe = (IgnoredPropertyException) cause;
            message = jpe.getPropertyName();
            responseException = errorBody(ex, CODE_40046.getMessage(), getCode(CODE_40046), message);
        } else if (cause instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException jpe = (UnrecognizedPropertyException) cause;
            message = jpe.getPropertyName();
            responseException = errorBody(ex, CODE_40040.getMessage(), getCode(CODE_40040), message);
        } else if (cause instanceof MismatchedInputException) {
            MismatchedInputException jpe = (MismatchedInputException) cause;
            message = jpe.getTargetType().getName();
            messageSecondReason = getCauseInsideQuotes(jpe.getPathReference());
            responseException = errorBody(ex, CODE_40041.getMessage(), getCode(CODE_40041), message, messageSecondReason);
        }
        return responseException;
    }
}