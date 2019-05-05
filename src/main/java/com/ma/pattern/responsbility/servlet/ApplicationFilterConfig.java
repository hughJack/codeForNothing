package com.ma.pattern.responsbility.servlet;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.StringManager;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.util.descriptor.web.FilterDef;

public final class ApplicationFilterConfig implements FilterConfig, Serializable {
    private static final long serialVersionUID = 1L;
    static final StringManager sm = StringManager.getManager("org.apache.catalina.core");
    private static final Log log = LogFactory.getLog(ApplicationFilterConfig.class);
    private static final List<String> emptyString = Collections.emptyList();
    private final transient Context context;
    private transient Filter filter = null;
    private final FilterDef filterDef;
    private transient InstanceManager instanceManager;
    private ObjectName oname;

    ApplicationFilterConfig(Context context, FilterDef filterDef) throws ClassCastException, ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException, InvocationTargetException, NamingException, IllegalArgumentException, NoSuchMethodException, SecurityException {
        this.context = context;
        this.filterDef = filterDef;
        if(filterDef.getFilter() == null) {
            this.getFilter();
        } else {
            this.filter = filterDef.getFilter();
            this.getInstanceManager().newInstance(this.filter);
            this.initFilter();
        }
    }
}