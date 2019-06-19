package com.ma.filter;

import com.ma.juc.example.threadLocal.RequestHolder;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(filterName = "HttpFilter")
@Slf4j
public class HttpFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 打印当前的请求路径
        log.info("do filter!{}{}",Thread.currentThread().getId(),((HttpServletRequest) req).getServletPath());
        RequestHolder.add(Thread.currentThread().getId());
        chain.doFilter(req,resp);// 让请求继续被处理
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
