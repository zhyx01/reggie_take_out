package com.ax.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.ax.reggie.common.BaseContext;
import com.ax.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * className: LoginCheckFilter
 * description: 登录验证拦截器
 *
 * @author: axiang
 * date: 2023/5/1 0001 11:29
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取本次请求的url
        String requestURI = request.getRequestURI();
        log.info("拦截到请求: {}", requestURI);

        // 2. 定义不需要拦截处理的请求路径
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/employee/page",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };

        // 3. 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 4. 如果不需要处理, 直接放行
        if (check) {
            log.info("本次请求的路径: {} 不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 5.1 判断登录状态, 如果以登录, 则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录..., 用户id为: {}", request.getSession().getAttribute("employee"));

            long id = Thread.currentThread().getId();
            log.info("当前线程id为: {}", id);

            // 将当前登录用户的id存入ThreadLocal中
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        // 5.2 判断登录状态, 如果以登录, 则直接放行 (移动端)
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录..., 用户id为: {}", request.getSession().getAttribute("user"));

            long id = Thread.currentThread().getId();
            log.info("当前线程id为: {}", id);

            // 将当前登录用户的id存入ThreadLocal中
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        // 6. 如果未登录, 则返回未登录结果, 通过输出流方式向客户端页面响应数据
        log.info("用户未登录...");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * description: 匹配路径, 检查本次请求是否需要放行
     * @param urls:
     * @param requestURI:
     * @return: boolean <br>
     * date: 2023/5/1 0001 <br>
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
