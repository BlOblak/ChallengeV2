package com.challenge.v2.utils;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;

public class ApiDocs {
    public ApiDocs() {
    }

    public static String desc(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        ApiModelProperty apiModelProperty = (ApiModelProperty)field.getAnnotation(ApiModelProperty.class);
        return apiModelProperty != null ? apiModelProperty.value() : null;
    }
}