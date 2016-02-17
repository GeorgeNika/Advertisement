/**
 * Ошибка, которую нежелательно показывать пользователю
 */

package ua.george_nika.advertisement.error;

import ua.george_nika.advertisement.util.AppLog;

public class NoUserFriendlyException extends RuntimeException {

    public NoUserFriendlyException(String loggerName, String className, String message, Throwable cause) {
        super(message, cause);
        AppLog.error(loggerName, className, message, cause);
    }
}
