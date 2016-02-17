/**
 * Класс для работы с ответом на запрос коректности каптчи.
 */
package ua.george_nika.advertisement.util;

@SuppressWarnings({"unused", "FieldCanBeLocal"})

public class CaptchaResponse {

    public boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
