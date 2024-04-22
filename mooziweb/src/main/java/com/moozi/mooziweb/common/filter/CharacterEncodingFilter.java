package com.moozi.mooziweb.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "characterEncodingFilter", initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8")
}, urlPatterns = "*")
public class CharacterEncodingFilter  implements Filter {
    String encoding;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //todo#8 UTF-8 인코딩, initParams의 encoding parameter value값을 charset 으로 지정합니다.
        //@WebFilter(initParams = {@WebInitParam(name = "encoding",value = "UTF-8")})
        servletRequest.setCharacterEncoding(encoding);

        filterChain.doFilter(servletRequest,servletResponse);
    }

}
