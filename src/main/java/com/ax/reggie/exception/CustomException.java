package com.ax.reggie.exception;

/**
 * className: CustomException
 * description: 自定义业务异常类
 *
 * @author: axiang
 * date: 2023/5/1 0001 19:39
 */
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
