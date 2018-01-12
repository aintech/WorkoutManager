package ru.aintech.workoutmanager.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Aintech
 */
public class MyServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
////        ServletRegistration.Dynamic myServlet = servletContext.addServlet("myServlet", MyServlet.class);
////        myServlet.addMapping("/custom/**");
//        DispatcherServlet ds = new DispatcherServlet();
//        ServletRegistration.Dynamic registration = servletContext.addServlet("appServlet", ds);
//        registration.addMapping("/");
//        registration.setMultipartConfig(new MultipartConfigElement("/tmp/workout/uploads"));
    }
}