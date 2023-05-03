package com.ax.reggie.controller;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.User;
import com.ax.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * className: UserController
 * description:
 *
 * @author: axiang
 * date: 2023/5/2 0002 11:21
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * description: 发送短信
     * @param user: 接收phone
     * @param session: 用来存验证码到session中
     * @return: R<String> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {

        return userService.sengMsg(user, session);
    }

    /**
     * description: 移动端用户登录
     * @param map: 接收页面提交的 phone, code
     * @param session: 获取存入的 code
     * @return: R<User> <br>
     * date: 2023/5/2 0002 <br>
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {

        log.info("map参数: {}", map.toString());

        return userService.login(map, session);

    }

}
