package com.vicras.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class InitializerConfig implements WebApplicationInitializer {

    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(DatabaseConfig.class);
        webContext.register(WebConfig.class);
        webContext.setServletContext(container);
        ServletRegistration.Dynamic reg = container.addServlet("dispatcherServlet", new DispatcherServlet(webContext));
        reg.setLoadOnStartup(1);
        reg.addMapping("*.action");
    }

}
