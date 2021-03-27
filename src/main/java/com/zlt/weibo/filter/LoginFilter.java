package com.zlt.weibo.filter;

import com.alibaba.fastjson.JSON;
import com.zlt.weibo.entity.Result;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "/*",initParams = {@WebInitParam(name="uri",value="/weibo/comments,/weibo/fabulous,/weibo/unfabulous,/user/follow,/user/unfollow,/user/update,/user/updateDo,/weibo/sendWeibo"),@WebInitParam(name="ajaxuri",value="/user/sendMessage,user/getFriends,/user/getMessage")})
public class LoginFilter implements Filter {

    private List<String> lists ;
    private List<String> ajaxLists ;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //判断当前的uri是否需要拦截
        // /weibo/user/update
        String uri = request.getRequestURI();
        //字符串替换  /weibo/user/update中将 /weibo 替换成空字符串
        //  /weibo/weibo/sendWeibo
//        uri = uri.replaceAll(request.getContextPath(),"");
        String contextPath = request.getContextPath();
        uri = uri.substring(uri.indexOf(contextPath) + contextPath.length() );
        if(lists.contains(uri)){//如果该路径是需要拦截的路径
            //判断是否已经登录
            Object o = request.getSession().getAttribute("CUR_USER");
            if(o == null){//没有登录
                response.sendRedirect(request.getContextPath() + "/user/login");
                return;
            }
        }
        if(ajaxLists.contains(uri)){//如果该路径是需要拦截的路径
            //判断是否已经登录
            Object o = request.getSession().getAttribute("CUR_USER");
            if(o == null){//没有登录
                Result result = new Result();
                result.setState(2);
                result.setMsg("未登录");
                response.getWriter().write(JSON.toJSONString(result));
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        String uris = config.getInitParameter("uri");
        //使用,进行分割  然后存到集合中去
        lists = Arrays.asList(uris.split(","));

        String ajaxuri = config.getInitParameter("ajaxuri");
        //使用,进行分割  然后存到集合中去
        ajaxLists = Arrays.asList(ajaxuri.split(","));
    }

}
