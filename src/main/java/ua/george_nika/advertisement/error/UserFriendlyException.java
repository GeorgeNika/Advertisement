/**
 * Ошибка, которую можно показывать пользователю
 */

package ua.george_nika.advertisement.error;

import ua.george_nika.advertisement.util.AppLog;

public class UserFriendlyException extends RuntimeException {

    public UserFriendlyException(String loggerName, String className, String message, Throwable cause) {
        super(cause.getMessage() + ". Because " + message);
        AppLog.error(loggerName, className, message, cause);
    }
}
