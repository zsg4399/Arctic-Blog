package com.hnust.zsg.utils;


import com.hnust.zsg.enumeration.ResultCodeType;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeType resultCodeType) {
        Result<T> result = build(body);
        result.setCode(resultCodeType.getCode());
        result.setMessage(resultCodeType.getMessage());
        return result;
    }

    public static <T> Result<T> build(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }


    public static<T> Result<T> ok(T data){
        return build(data, ResultCodeType.SUCCESS);
    }

    /**
     * 设置状态码
     * @param data
     * @param resultCodeType
     * @param <T>
     * @return
     */
    public static<T> Result<T> set(T data, ResultCodeType resultCodeType){
        return build(data, resultCodeType);
    }

    public static<T> Result<T> set(ResultCodeType resultCodeType){
        return build(null, resultCodeType);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeType.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeType.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
