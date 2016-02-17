/**
 * Класс служит для работы с объектом Account.
 * Использует framework Spring MVC.
 * Created by george on 11.02.2016.
 */

package ua.george_nika.advertisement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.george_nika.advertisement.dao.AccountRepository;
import ua.george_nika.advertisement.error.AccessDeniedException;
import ua.george_nika.advertisement.error.ApplicationError;
import ua.george_nika.advertisement.error.LoginActionException;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.util.AppConst;
import ua.george_nika.advertisement.util.AppLog;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private static String LOGGER_NAME = AppLog.SERVICE;
    private static String CLASS_NAME = AccountService.class.getName();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    Validator validator;

    /**
     * Возвращает объект Account по имени
     *
     * @param name - имя искомого Account
     */
    public Account getAccountByName(String name) {
        try {
            return accountRepository.getAccountByLogin(name);
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get account by name - " + name, ex);
        }
    }

    /**
     * Возвращает количестко объект Account по имени
     *
     * @param login - имя искомого Account
     */
    public int getCountAccountsByLogin(String login) {
        try {
            return accountRepository.getCountAccountsByLogin(login);
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get count accounts with name - " + login, ex);
        }
    }

    /**
     * Выполняет процесс аутентификации
     *
     * @param account - объект с информацией предоставленной пользователем для аутентификации
     * @param session - объект с текущей сессией на сервере
     * @return - строка с кодом успешного выполнения или сообщение об ошибке
     */
    public String signInAction(Account account, HttpSession session) {
        try {

            // validate account
            Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
            if (constraintViolations.size() > 0) {
                String validationResult = constraintViolations.iterator().next().getMessage();
                AppLog.userInfo(LOGGER_NAME, session, "Sign in failed - " + validationResult);
                return validationResult;
            }

            return getCheckAccountString(account, session);

        } catch (RuntimeException ex) {
            throw new LoginActionException(LOGGER_NAME, CLASS_NAME, "Error while sign in action", ex);
        }
    }

    /**
     * Выполняет процесс создания нового пользователя
     *
     * @param account - объект с информацией предоставленной пользователем для аутентификации
     * @param session - объект с текущей сессией на сервере
     * @return - строка с кодом успешного выполнения или сообщение об ошибке
     */
    public String joinAction(Account account, HttpSession session) {
        try {

            // validate account
            Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
            if (constraintViolations.size() > 0) {
                String validationResult = constraintViolations.iterator().next().getMessage();
                AppLog.userInfo(LOGGER_NAME, session, "Join failed - " + validationResult);
                return validationResult;
            }

            // check for existing
            if (getCountAccountsByLogin(account.getLogin()) > 0) {
                AppLog.userInfo(LOGGER_NAME, session, "Join failed. Login - " + account.getLogin() + " already exist.");
                return "Sorry. Login already exist. Try another login.";
            }

            accountRepository.saveAndFlush(account);
            AppLog.userInfo(LOGGER_NAME, session, "Create new account. Login - " + account.getLogin());

            return getCheckAccountString(account, session);

        } catch (RuntimeException ex) {
            throw new LoginActionException(LOGGER_NAME, CLASS_NAME, "Error while join action", ex);
        }
    }

    private boolean isCorrectAccount(String login, String password) {
        try {
            Account account = accountRepository.getAccountByLogin(login);
            if (account == null) {
                return false;
            }
            if (password.equals(account.getPassword())) {
                return true;
            }
            return false;
        } catch (RuntimeException ex) {
            throw new LoginActionException(LOGGER_NAME, CLASS_NAME, "Error while check account", ex);
        }
    }

    private void successSignInSession(String login, HttpSession session) {
        AppLog.userInfo(LOGGER_NAME, session, "Success Sign In " + login);
        session.setAttribute(AppConst.SIGNIN_IN_SESSION, true);
        session.setAttribute(AppConst.USER_IN_SESSION, login);
    }

    private String getCheckAccountString(Account account, HttpSession session) {
        if (isCorrectAccount(account.getLogin(), account.getPassword())) {
            successSignInSession(account.getLogin(), session);
            return AppConst.OK_RESPONSE;
        } else {
            AppLog.userInfo(LOGGER_NAME, session, "Sign in failed. Login - " + account.getLogin());
            return "incorrect login or password";
        }
    }

    /**
     * Проверяет сессию на наличие залогиненого пользователя
     *
     * @param session - объект с текущей сессией на сервере
     */
    public void checkPermission(HttpSession session) {
        try {
            Object login = session.getAttribute(AppConst.SIGNIN_IN_SESSION);
            if ((login == null) || (!(boolean) login)) {
                throw new RuntimeException();
            }
        } catch (RuntimeException ex) {
            AppLog.userInfo(AppLog.CONTROLLER, session, "Try execute action without permission.");
            throw new AccessDeniedException(LOGGER_NAME, CLASS_NAME, "Try execute action without permission.", ex);
        }
    }
}
