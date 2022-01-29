package ru.gb.antonov.lesson6;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter (urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST})
public class FilterHtmlGeneral implements Filter
{
    private transient FilterConfig filterConfig;

    @Override public void init (FilterConfig filterConfig) {    this.filterConfig = filterConfig;    }

    @Override public void doFilter (ServletRequest requ, ServletResponse resp, FilterChain chain)
                          throws IOException, ServletException
    {
        resp.setContentType ("text/html; charset=utf-8");
        chain.doFilter (requ, resp);
    }

    @Override public void destroy() {}
}
