package com.yokayo.appname.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        
        // регистрируем контекст и контроллер
        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
        dispatcherServletContext.register(DispatcherServletContextConfig.class);
        
        ServletRegistration.Dynamic frontServlet = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(dispatcherServletContext));
        frontServlet.setLoadOnStartup(1);
        frontServlet.addMapping("/api/*");
    }
}
