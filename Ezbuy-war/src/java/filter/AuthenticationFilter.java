/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managedbean.HelperManagedBean;

/**
 *
 * @author victor
 */
public class AuthenticationFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
//get the managed bean
        HelperManagedBean bean;

        HttpSession session = request1.getSession(false);
        if (session == null) {
            bean = null;
        } else {
            bean = (HelperManagedBean) session.getAttribute("helperManagedBean");
        }

        if (bean == null || bean.getEzuser() == null) {
            ((HttpServletResponse) response).sendRedirect(request1.getContextPath() + "/index.xhtml");
        } else {
//authenticated - continue
            chain.doFilter(request1, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//do nothing
    }

    @Override
    public void destroy() {
//do nothing
    }
}
