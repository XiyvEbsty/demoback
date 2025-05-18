package com.example.common;

public class Result<T> {
    private String code;
    private T data;
    private String message;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode("200");
        result.setMessage("请求成功");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setMessage("请求成功");
        result.setCode("200");
        return result;
    }
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode("500");
        result.setMessage(message);
        return result ;
    }
    public static <T> Result<T> error(String code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
