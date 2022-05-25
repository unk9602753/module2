package com.epam.esm.controller;

import com.epam.esm.entity.ResponseException;
import com.epam.esm.config.Translator;
import com.epam.esm.exception.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import static com.epam.esm.entity.ErrorCode.CODE_40001;
import static com.epam.esm.entity.ErrorCode.CODE_40002;
import static com.epam.esm.entity.ErrorCode.CODE_40003;
import static com.epam.esm.entity.ErrorCode.CODE_40004;
import static com.epam.esm.entity.ErrorCode.CODE_40005;
import static com.epam.esm.entity.ErrorCode.CODE_40006;
import static com.epam.esm.entity.ErrorCode.CODE_40007;
import static com.epam.esm.entity.ErrorCode.CODE_40008;
import static com.epam.esm.entity.ErrorCode.CODE_40009;
import static com.epam.esm.entity.ErrorCode.CODE_40010;
import static com.epam.esm.entity.ErrorCode.CODE_40011;
import static com.epam.esm.entity.ErrorCode.CODE_40012;
import static com.epam.esm.entity.ErrorCode.CODE_40013;
import static com.epam.esm.entity.ErrorCode.CODE_40014;
import static com.epam.esm.entity.ErrorCode.CODE_40015;
import static com.epam.esm.entity.ErrorCode.CODE_40016;
import static com.epam.esm.entity.ErrorCode.CODE_40017;
import static com.epam.esm.entity.ErrorCode.CODE_40018;
import static com.epam.esm.entity.ErrorCode.CODE_40019;
import static com.epam.esm.entity.ErrorCode.CODE_40020;
import static com.epam.esm.entity.ErrorCode.getCode;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidDefinitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleInvalidDefinitionException(InvalidDefinitionException e) {
        return errorBody(e, CODE_40004.getMessage(), getCode(CODE_40004));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return errorBody(e, CODE_40005.getMessage(), HttpServletResponse.SC_BAD_REQUEST, getCause(e.getMessage()));
    }

    @ExceptionHandler(MysqlDataTruncation.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleMysqlDataTruncation(MysqlDataTruncation e) {
        return errorBody(e, CODE_40005.getMessage(), HttpServletResponse.SC_BAD_REQUEST, getCause(e.getMessage()));
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleBadSqlGrammarException(BadSqlGrammarException e) {
        return errorBody(e, CODE_40006.getMessage(), getCode(CODE_40006), getCause(e.getMessage()));
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleServiceException(ServiceException e) {
        return errorBody(e, e.getErrorCode().getMessage(), HttpServletResponse.SC_BAD_REQUEST, e.getId(), e.getConcreteMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return errorBody(e, CODE_40013.getMessage(), getCode(CODE_40013));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseException handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, WebRequest request) {
        return errorBody(e, CODE_40011.getMessage(), getCode(CODE_40011), e.getMethod());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseException handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        return errorBody(e, CODE_40014.getMessage(), getCode(CODE_40014));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        return errorBody(e, CODE_40009.getMessage(), getCode(CODE_40009));
    }


    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseException handleTypeMismatch(TypeMismatchException e) {
        return errorBody(e, CODE_40007.getMessage(), getCode(CODE_40007),e.getRequiredType().getName(),e.getRootCause().toString());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleHttpMessageNotWritable(HttpMessageNotWritableException e) {
        return errorBody(e, CODE_40013.getMessage(), getCode(CODE_40013));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleMissingServletRequestPart(MissingServletRequestPartException e) {
        return errorBody(e, CODE_40009.getMessage(), getCode(CODE_40009));
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleBindException(BindException e) {
        return errorBody(e, CODE_40017.getMessage(), getCode(CODE_40017));
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseException handleConversionNotSupportedException(ConversionNotSupportedException e) {
        return errorBody(e, CODE_40016.getMessage(), getCode(CODE_40016));
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleJsonParseException(JsonParseException e) {
        return errorBody(e, "exception.parse.json", HttpServletResponse.SC_BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return errorBody(e, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseException handleNoSuchElementException(NoSuchElementException e) {
        return errorBody(e, CODE_40019.getMessage(), getCode(CODE_40019));
    }

    private String getCause(String message) {
        return message.substring(message.indexOf("'") + 1, message.lastIndexOf("'"));
    }

    private ResponseException errorBody(Throwable e, String messageCode, int code, String... args) {
        ResponseException responseException = new ResponseException();
        responseException.setCode(code);
        responseException.setMessage(String.format(Translator.toLocale(messageCode), args));
        return responseException;
    }
}