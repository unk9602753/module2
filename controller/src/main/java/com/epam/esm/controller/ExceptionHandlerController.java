package com.epam.esm.controller;

import com.epam.esm.entity.ResponseException;
import com.epam.esm.config.Translator;
import com.epam.esm.exception.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<ResponseException> handleInvalidDefinitionException(InvalidDefinitionException e) {
        ResponseException re = errorBody(e, CODE_40004.getMessage(), getCode(CODE_40004));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseException> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ResponseException re = errorBody(e, CODE_40005.getMessage(), HttpServletResponse.SC_BAD_REQUEST, getCause(e.getMessage()));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<ResponseException> handleBadSqlGrammarException(BadSqlGrammarException e) {
        ResponseException re = errorBody(e, CODE_40006.getMessage(), getCode(CODE_40006), getCause(e.getMessage()));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseException> handleServiceException(ServiceException e) {
        ResponseException re = errorBody(e, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST, e.getId(), e.getConcreteMessage());
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseException> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        ResponseException re = errorBody(e, CODE_40013.getMessage(), getCode(CODE_40013));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseException> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        ResponseException re = errorBody(e, CODE_40011.getMessage(), getCode(CODE_40011));
        return new ResponseEntity<>(re, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseException> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e) {
        ResponseException re = errorBody(e, CODE_40014.getMessage(), getCode(CODE_40014));
        return new ResponseEntity<>(re, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseException> handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        ResponseException re = errorBody(e, CODE_40009.getMessage(), getCode(CODE_40009));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ResponseException> handleTypeMismatch(TypeMismatchException e) {
        ResponseException re = errorBody(e, CODE_40007.getMessage(), getCode(CODE_40007));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<ResponseException> handleHttpMessageNotWritable(HttpMessageNotWritableException e) {
        ResponseException re = errorBody(e, CODE_40013.getMessage(), getCode(CODE_40013));
        return new ResponseEntity<>(re, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ResponseException> handleMissingServletRequestPart(MissingServletRequestPartException e) {
        ResponseException re = errorBody(e, CODE_40009.getMessage(), getCode(CODE_40009));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseException> handleBindException(BindException e) {
        ResponseException re = errorBody(e, CODE_40017.getMessage(), getCode(CODE_40017));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<ResponseException> handleConversionNotSupportedException(ConversionNotSupportedException e) {
        ResponseException re = errorBody(e, CODE_40016.getMessage(), getCode(CODE_40016));
        return new ResponseEntity<>(re, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ResponseException> handleJsonParseException(JsonParseException e) {
        ResponseException re = errorBody(e, "exception.parse.json", HttpServletResponse.SC_BAD_REQUEST);
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ResponseException> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        ResponseException re = errorBody(e, e.getMessage(), HttpServletResponse.SC_NOT_FOUND);
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseException> handleNoSuchElementException(NoSuchElementException e) {
        ResponseException re = errorBody(e, CODE_40019.getMessage(), getCode(CODE_40019));
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    private String getCause(String message) {
        return message.substring(message.indexOf("'") + 1, message.lastIndexOf("'"));
    }

    private ResponseException errorBody(Throwable e, String messageCode, int code, String... args) {
        ResponseException re = new ResponseException();
        re.setCode(code);
        re.setMessage(String.format(Translator.toLocale(messageCode), args));
        return re;
    }
}