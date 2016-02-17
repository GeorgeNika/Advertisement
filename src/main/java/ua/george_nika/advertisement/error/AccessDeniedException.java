/**
 * Ошибка при выполнении действия без разришения
 */

package ua.george_nika.advertisement.error;

public class AccessDeniedException extends NoUserFriendlyException {

    public AccessDeniedException(String loggerName, String className, String message, Throwable cause) {
        super(loggerName, className, message, cause);
    }
}
