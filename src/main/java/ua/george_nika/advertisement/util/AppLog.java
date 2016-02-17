/**
 * Proxy for log framework
 * now in application use log4j2
 */

package ua.george_nika.advertisement.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import javax.servlet.http.HttpSession;

@SuppressWarnings("unused")

public class AppLog {

    private static String CLASS_NAME = AppLog.class.getSimpleName();

    public static final String CONTROLLER = "Controller";
    public static final String MODEL = "Model";
    public static final String DAO = "Dao";
    public static final String SERVICE = "Service";
    public static final String UTIL = "Util";

    private AppLog() {
    }

    public static void initApplicationLog() {
        System.setProperty(AppConst.APPLICATION_LOG_PATH, AppConst.getPathApplicationLog());
        System.setProperty(AppConst.APPLICATION_ARCHIVE_LOG_PATH, AppConst.getPathArchiveApplicationLog());
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.reconfigure();

    }

    public static void userInfo(String logger, HttpSession session, String message) {
        StringBuilder userIdString = new StringBuilder();
        userIdString.append("id - ").append(session.getId());

        Object login = session.getAttribute(AppConst.SIGNIN_IN_SESSION);
        if ((login != null) && ((boolean) login)) {
            userIdString.append(" login - ").append(session.getAttribute(AppConst.USER_IN_SESSION));
        }
        userIdString.append(" * ");

        Logger log = LogManager.getLogger(logger);
        log.info(userIdString.toString() + message);
    }

    public static void info(String logger, String message) {
        Logger log = LogManager.getLogger(logger);
        log.info(message+"\n");
    }

    public static void info(String logger, String className, String message) {
        Logger log = LogManager.getLogger(logger);
        log.info("[class-" + className + "] * " + message);
    }

    public static void error(String logger, String className, String message) {
        Logger log = LogManager.getLogger(logger);
        log.error("[class-" + className + "] * " + message);
    }

    public static void error(String logger, String className, String message, Throwable ex) {
        Logger log = LogManager.getLogger(logger);
        log.error("[class-" + className + "] * " + message, ex);
    }

    public static void warn(String logger, String className, String message) {
        Logger log = LogManager.getLogger(logger);
        log.warn("[class-" + className + "] * " + message);
    }

}
