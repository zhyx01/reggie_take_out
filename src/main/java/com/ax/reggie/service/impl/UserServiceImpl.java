package com.ax.reggie.service.impl;

import com.ax.reggie.common.R;
import com.ax.reggie.entity.User;
import com.ax.reggie.mapper.UserMapper;
import com.ax.reggie.service.UserService;
import com.ax.reggie.utils.SMSUtils;
import com.ax.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * className: UserServiceImpl
 * description:
 *
 * @author: axiang
 * date: 2023/5/2 0002 11:21
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private RedisTemplate redisTemplate;


    /** 签名 */
    public static final String SIGN_NAME = "axiang";
    /** 模板CODE */
    public static final String TEMPLATE_CODE = "SMS_460675376";

    @Override
    public R<String> sengMsg(User user, HttpSession session) {

        // 获取手机号
        String phone = user.getPhone();
        if (phone != null) {
            // 随机生成验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();

            log.info("随机生成的验证码是: {}", code);

            // 调用阿里云提供的短信服务API完成发送短信
            SMSUtils.sendMessage(SIGN_NAME, TEMPLATE_CODE, phone, code);

            // 将生成的短信保存到session
            //session.setAttribute(phone, code);

            // 将生成的验证码存入redis中
            redisTemplate.opsForValue().set("login:code:" + phone, code, 5, TimeUnit.MINUTES);

            log.info("存入redis的验证码是: {}", code);

            return R.success("短信发送成功");
        }

        return R.error("短信发送失败, 请稍后重试!");
    }

    @Override
    public R<User> login(Map map, HttpSession session) {

        // 获取手机号
        String phone = map.get("phone").toString();

        // 获取验证码
        String code = map.get("code").toString();

        // 从session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        // 从redis中获取验证码
        Object codeInSession = redisTemplate.opsForValue().get("login:code:" + phone);

        log.info("redis中保存的验证码是: {}", codeInSession);

        // 进行验证码的比对 (页面提交的和session中存的进行比对)
        if (codeInSession != null && codeInSession.equals(code)) {
            // 一致, 说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = getOne(queryWrapper);

            // 判断当前手机号对应的是否是新用户, 如果是新用户就自动完成注册
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                save(user);
            }
            session.setAttribute("user", user.getId());

            // 登录成功, 删除redis中的验证码
            redisTemplate.delete("login:code:" + phone);

            return R.success(user);
        }
        return R.error("登录失败");
    }
}
