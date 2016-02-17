/**
 * Ошибка при аутентификации
 */

package ua.george_nika.advertisement.error;

public class LoginActionException extends UserFriendlyException {

    public LoginActionException(String loggerName, String className, String message, Throwable cause) {
        super(loggerName, className, message, cause);
    }
}
