package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CODE_40004("exception.InvalidDefinitionException"),
    CODE_40005("exception.SQLIntegrityConstraintViolationException"),
    CODE_40006("exception.BadSqlGrammarException"),
    CODE_40007("exception.TypeMismatchException"),
    CODE_40008("exception.NoSuchRequestHandlingMethodException"),
    CODE_40009("exception.MissingServletRequestParameterException"),
    CODE_40010("exception.MethodArgumentNotValidException"),
    CODE_40011("exception.HttpRequestMethodNotSupportedException"),
    CODE_40012("exception.HttpMessageNotWritableException"),
    CODE_40013("exception.HttpMessageNotReadableException"),
    CODE_40014("exception.HttpMediaTypeNotSupportedException"),
    CODE_40015("exception.HttpMediaTypeNotAcceptableException"),
    CODE_40016("exception.ConversionNotSupportedException"),
    CODE_40017("exception.bind"),
    CODE_40001("exception.update.certificate"),
    CODE_40002("exception.create.certificate"),
    CODE_40003("exception.find.certificate"),
    CODE_40018("exception.id.exist"),
    CODE_40019("exception.no.values.present"),
    CODE_40020("exception.delete.certificate"),
    CODE_40021("exception.incorrect.search.criteria"),
    CODE_40022("exception.method.not.support"),
    CODE_40023("exception.syntax.query"),
    CODE_40025("exception.parse.json"),
    CODE_40026("exception.find.tag"),
    CODE_40027("exception.syntax.query"),
    CODE_40028("exception.create.tag"),
    CODE_40029("exception.delete.tag"),
    CODE_40030("exception.not.found.with.param"),

    CODE_40031("exception.name.not.be.null"),
    CODE_40032("exception.name.out.of.range"),
    CODE_40033("exception.description.not.be.null"),
    CODE_40034("exception.description.out.of.range"),
    CODE_40035("exception.price.min.out.of.range"),
    CODE_40036("exception.price.max.out.of.range"),
    CODE_40037("exception.duration.min.out.of.range"),
    CODE_40039("exception.criteria.not.valid"),
    CODE_40040("exception.parse.field"),
    CODE_40041("exception.parse.type"),
    CODE_40042("exception.update.name.size"),
    CODE_40043("exception.update.description.size"),
    CODE_40044("exception.update.price.value"),
    CODE_40045("exception.update.duration.value"),
    CODE_40046("exception.parse.id"),
    CODE_40047("exception.tag.name.null"),
    CODE_40048("exception.tag.name.size"),
    CODE_40038("exception.duration.max.out.of.range");


    String message;

    public static int getCode(ErrorCode errorCode){
        String name = errorCode.name();
        return Integer.parseInt(name.substring(name.indexOf("_")+1));
    }

    public static ErrorCode getErrorCode(String label) {
        for (ErrorCode e : values()) {
            if (e.message.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
