package com.vicras.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class InitializerConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan("com.vicras");
        sc.addListener(new ContextLoaderListener(root));
        root.register(DatabaseConfig.class);
        root.register(WebConfig.class);
        root.register(MailConfig.class);
        root.register(AsyncConfig.class);
        root.register(RedisConfig.class);
        root.register(SecurityConfig.class);

        ServletRegistration.Dynamic appServlet = sc.addServlet("springServlet",
                new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }
}
