package com.ian.filter;

import com.alibaba.fastjson.JSON;
import com.ian.entity.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;

        //1.獲取本次請求的URI
        String requestURI = request.getRequestURI();

        log.info("攔截到的請求：{}",requestURI);
        //定義不需要處理的請求路徑
        String[] rules =new String[]{"/employee/login","/employee/logout","/backend/**","/front/**","/common/**"};
        //2.判斷本次請求是否需要處理
        boolean check = check(rules, requestURI);
        //3.如果不需要處理直接放行
        if(check){
            log.info("本次請求{}不需要處理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //判斷是否完成登入，若已登入則直接放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("用戶已登入，id為{}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        log.info("本次請求{}未登入",requestURI);
        //如果未登入結果，通過輸出流方式向客戶端頁面響應數據
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;
    }

    /**
     * 路徑匹配，判斷本次請求是否需要放行
     * @param urles
     * @param requestURI
     * @return
     */

    public boolean check(String[] urles, String requestURI){
        for (String urle : urles) {
            boolean match = PATH_MATCHER.match(urle, requestURI);
            if(match){
                return true;
            }

        }
return false;

    }
}
