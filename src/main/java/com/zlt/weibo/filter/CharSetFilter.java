package com.zlt.weibo.filter;


import com.zlt.weibo.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
@WebFilter("/*")
public class CharSetFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //请求乱码过滤器
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getMethod().equalsIgnoreCase("get")){//如果是get请求
            //装饰者模式  设计模式
            req = new Request(req);
        }else{//如果是post请求
            req.setCharacterEncoding("UTF-8");
        }
        //放行
        chain.doFilter(req,res);

    }

    @Override
    public void destroy() {

    }

    class Request extends HttpServletRequestWrapper{

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request
         * @throws IllegalArgumentException if the request is null
         */
        public Request(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);//其实就是调用了构造传进来的request的getParameter方法
            if(StringUtils.isNotNull(value)){
                try {
                    value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return value;
        }
    }
}
