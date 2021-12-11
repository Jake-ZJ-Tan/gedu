package com.guli.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class LoginFilter extends ZuulFilter {

    /**
     * 过滤器类型 ERROR_TYPE POST_TYPE PRE_TYPE ROUTE_TYPE
     *
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器执行顺序， 值越小优先级越高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行run()方法（过滤）
     * true 执行
     * false 不执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        String requestURI = RequestContext.getCurrentContext().getRequest().getRequestURI();
        // 鉴权URL(项目中我们会在数据库或缓存中取出ACL列表)
        String authUrl = "/guli-vod/front/vod/";
        if (!StringUtils.isEmpty(requestURI) && requestURI.toLowerCase().contains(authUrl)) {
            return true; // 执行run()方法
        }
        // 放行
        return false;
    }

    /**
     * 核心方法
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // System.err.println("run()执行");
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();
        String token;
        if (StringUtils.isEmpty((token = request.getHeader("token")))) {
            token = request.getParameter("token");
        }

        // 登录校验逻辑  根据公司情况自定义 JWT
        if (StringUtils.isEmpty(token)) {
            // 设置为false：请求不会继续转发到下游服务器
            requestContext.setSendZuulResponse(false);
            // 设置返回的响应码
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }

}
