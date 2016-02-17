/**
 * Set`s attribute "context"
 * and also log when start or stop application
 */

package ua.george_nika.advertisement.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitContextListener implements ServletContextListener {

    private static String LOGGER_NAME = AppLog.CONTROLLER;

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();
        String contextPath = context.getContextPath();
        context.setAttribute("context", contextPath);

        AppLog.initApplicationLog();
        AppLog.info(LOGGER_NAME, "Program has been started");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        AppLog.info(LOGGER_NAME, "Program has been destroyed");
    }
}
