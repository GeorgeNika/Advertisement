/**
 * Here placed all global application constants
 */
package ua.george_nika.advertisement.util;

import org.springframework.stereotype.Component;

@SuppressWarnings("unused")

@Component
public class AppConst {

    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String APPLICATION_LOG_PATH = "LogPath";
    public static final String APPLICATION_ARCHIVE_LOG_PATH = "ArchiveLogPath";

    public static final String USER_IN_SESSION = "user";
    public static final String SIGNIN_IN_SESSION = "isUserSignIn";
    public static final String PAGE_IN_SESSION = "pageInSession";
    public static final String FILTER_IN_SESSION = "filterInSession";

    public static final String OK_RESPONSE = "OK";

    public static final int MESSAGE_ON_PAGE = 8;

    private static String pathApplicationLog;
    private static String pathArchiveApplicationLog;


    public static String getPathApplicationLog() {
        return pathApplicationLog;
    }

    public static String getPathArchiveApplicationLog() {
        return pathArchiveApplicationLog;
    }

    private static void setPathApplicationLog(String path) {
        // run from dispatcher-servlet.xml
        AppConst.pathApplicationLog = path.replace(" ", "").replace("\n", "");
    }

    public static void setPathArchiveApplicationLog(String path) {
        // run from dispatcher-servlet.xml
        AppConst.pathArchiveApplicationLog = path.replace(" ", "").replace("\n", "");
    }
}

