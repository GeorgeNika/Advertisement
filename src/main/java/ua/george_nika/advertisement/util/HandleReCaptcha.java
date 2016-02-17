/**
 * Класс для проверки коректности каптчи.
 */
package ua.george_nika.advertisement.util;

import com.google.gson.Gson;
import ua.george_nika.advertisement.error.ApplicationError;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("FieldCanBeLocal")

public class HandleReCaptcha {

    private static String LOGGER_NAME = AppLog.UTIL;
    private static String CLASS_NAME = HandleReCaptcha.class.getCanonicalName();

    /**
     * Проверяем правильность каптчи
     *
     * @param request        - объект текущего запроса
     * @param reCaptcha      - данные от каптчи на html странице
     * @param captchaPrivate - приватный ключ для проверки правильности каптчи
     */
    public static boolean isGoodCaptcha(HttpServletRequest request, String reCaptcha, String captchaPrivate) {

        URL url;
        try {
            url = new URL("https://www.google.com/recaptcha/api/siteverify?secret="
                    + captchaPrivate + "&response=" + reCaptcha + "&remoteip=" + request.getRemoteAddr());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line, outputString = "";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                outputString += line;
            }

            CaptchaResponse capRes = new Gson().fromJson(outputString, CaptchaResponse.class);
            return capRes.isSuccess();
        } catch (IOException e) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Trouble when try check captcha", e);
        }
    }

}
