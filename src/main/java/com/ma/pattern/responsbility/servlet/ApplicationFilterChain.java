package com.ma.pattern.responsbility.servlet;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netscape.security.Principal;
import org.apache.catalina.Globals;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.security.SecurityUtil;
import org.apache.naming.StringManager;
import org.apache.tomcat.util.ExceptionUtils;

public final class ApplicationFilterChain implements FilterChain {
    private static final ThreadLocal<ServletRequest> lastServicedRequest;
    private static final ThreadLocal<ServletResponse> lastServicedResponse;
    public static final int INCREMENT = 10;
    private ApplicationFilterConfig[] filters = new ApplicationFilterConfig[0];
    private int pos = 0;
    private int n = 0;
    private Servlet servlet = null;
    private boolean servletSupportsAsync = false;
    private static final StringManager sm;
    private static final Class<?>[] classType;
    private static final Class<?>[] classTypeUsedInService;

    public ApplicationFilterChain() {
    }

    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if(Globals.IS_SECURITY_ENABLED) {
            final ServletRequest req = request;
            final ServletResponse res = response;

            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction() {
                    public Void run() throws ServletException, IOException {
                        ApplicationFilterChain.this.internalDoFilter(req, res);
                        return null;
                    }
                });
            } catch (PrivilegedActionException var7) {
                Exception e = var7.getException();
                if(e instanceof ServletException) {
                    throw (ServletException)e;
                }

                if(e instanceof IOException) {
                    throw (IOException)e;
                }

                if(e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                }

                throw new ServletException(e.getMessage(), e);
            }
        } else {
            this.internalDoFilter(request, response);
        }

    }

    private void internalDoFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if(this.pos < this.n) {
            ApplicationFilterConfig e1 = this.filters[this.pos++];

            try {
                Filter res1 = e1.getFilter();
                if(request.isAsyncSupported() && "false".equalsIgnoreCase(e1.getFilterDef().getAsyncSupported())) {
                    request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", Boolean.FALSE);
                }

                if(Globals.IS_SECURITY_ENABLED) {
                    Principal principal1 = ((HttpServletRequest)request).getUserPrincipal();
                    Object[] args1 = new Object[]{request, response, this};
                    SecurityUtil.doAsPrivilege("doFilter", res1, classType, args1, principal1);
                } else {
                    res1.doFilter(request, response, this);
                }

            } catch (ServletException | RuntimeException | IOException var15) {
                throw var15;
            } catch (Throwable var16) {
                Throwable res = ExceptionUtils.unwrapInvocationTargetException(var16);
                ExceptionUtils.handleThrowable(res);
                throw new ServletException(sm.getString("filterChain.filter"), res);
            }
        } else {
            try {
                if(ApplicationDispatcher.WRAP_SAME_OBJECT) {
                    lastServicedRequest.set(request);
                    lastServicedResponse.set(response);
                }

                if(request.isAsyncSupported() && !this.servletSupportsAsync) {
                    request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", Boolean.FALSE);
                }

                if(request instanceof HttpServletRequest && response instanceof HttpServletResponse
                    && Globals.IS_SECURITY_ENABLED) {
                    Principal principal = ((HttpServletRequest)request).getUserPrincipal();
                    Object[] args = new Object[]{request, response};
                    SecurityUtil.doAsPrivilege("service", this.servlet, classTypeUsedInService, args, principal);
                } else {
                    this.servlet.service(request, response);
                }
            } catch (ServletException | RuntimeException | IOException var17) {
                throw var17;
            } catch (Throwable var18) {
                Throwable e = ExceptionUtils.unwrapInvocationTargetException(var18);
                ExceptionUtils.handleThrowable(e);
                throw new ServletException(sm.getString("filterChain.servlet"), e);
            } finally {
                if(ApplicationDispatcher.WRAP_SAME_OBJECT) {
                    lastServicedRequest.set((Object)null);
                    lastServicedResponse.set((Object)null);
                }

            }

        }
    }

    ...
}
