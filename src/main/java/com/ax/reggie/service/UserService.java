package com.ax.reggie.service;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends IService<User> {

    /**
     * description: 发送短信
     *
     * @param user    : 接收phone
     * @param session
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<String> sengMsg(User user, HttpSession session);

    /**
     * description: 移动端用户登录
     * @param map: 接收页面提交的 phone, code
     * @param session: 获取存入的 code
     * @return: R<User> <br>
     * date: 2023/5/2 0002 <br>
     */
    R<User> login(Map map, HttpSession session);
}
