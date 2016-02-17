/**
 * Общая щоибка при выполнении действия
 */

package ua.george_nika.advertisement.error;

public class ApplicationError extends UserFriendlyException {

    public ApplicationError(String loggerName, String className, String message, Throwable cause) {
        super(loggerName, className, message, cause);
    }
}
