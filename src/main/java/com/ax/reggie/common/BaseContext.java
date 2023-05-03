package com.ax.reggie.common;

/**
 * className: BaseContext
 * description: 基于ThreadLocal封装工具类, 用于保存和获取当前登录的用户id
 *
 * @author: axiang
 * date: 2023/5/1 0001 17:46
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * description: 将当前登录的用户id存入ThreadLocal
     * @param id:
     * @return: void <br>
     * date: 2023/5/1 0001 <br>
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * description: 从ThreadLocal中获取当前登录用户的id
     * @param :
     * @return: Long <br>
     * date: 2023/5/1 0001 <br>
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
