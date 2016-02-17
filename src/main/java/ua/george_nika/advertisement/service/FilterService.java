/**
 * Класс служит для работы с объектом Filter.
 * Использует framework Spring MVC.
 * Created by george on 16.02.2016.
 */

package ua.george_nika.advertisement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.george_nika.advertisement.error.ApplicationError;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Filter;
import ua.george_nika.advertisement.util.AppConst;
import ua.george_nika.advertisement.util.AppLog;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Service
public class FilterService {

    private static String LOGGER_NAME = AppLog.SERVICE;
    private static String CLASS_NAME = FilterService.class.getName();

    @Autowired
    CategoryService categoryService;

    @Autowired
    AccountService accountService;

    @Autowired
    Validator validator;

    /**
     * Возвращает текущий фильтр или создает новый пустой.
     *
     * @param session - объект с текущей сессией на сервере
     */
    public Filter getCurrentFilter(HttpSession session) {
        try {
            Filter currentFilter;
            if (session.getAttribute(AppConst.FILTER_IN_SESSION) == null) {
                currentFilter = new Filter(categoryService.getCategoryList());
            } else {
                currentFilter = (Filter) session.getAttribute(AppConst.FILTER_IN_SESSION);
            }
            return currentFilter;
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get current filter", ex);
        }

    }

    /**
     * Возвращает список категорий выбранных в текущемй фильтре.
     *
     * @param filter - объект с текущим фильтром
     */
    public List<Category> getFilteredCategoryList(Filter filter) {
        try {
            List<Category> resultList = new ArrayList<>();
            Map<Category, Boolean> categoryMap = filter.getCategoryMap();
            for (Category loopCategory : categoryMap.keySet()) {
                if (categoryMap.get(loopCategory)) {
                    resultList.add(loopCategory);
                }
            }
            if (resultList.size() == 0) {
                return null;
            }
            return resultList;
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get filtered category list", ex);
        }
    }

    /**
     * Возвращает Account выбранный в текущемй фильтре.
     *
     * @param filter - объект с текущим фильтром
     */
    public Account getFilteredAccount(Filter filter) {
        try {
            return accountService.getAccountByName(filter.getAuthorName());
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get filtered account", ex);
        }
    }

    /**
     * Устанавливает новый фильтр при выходе пользователя из системы.
     * В фильтре сбрасываются дынные о показе только моих сообщений.
     * И если имя автора в фильтре совпадало с текущим именем то оно очищается.
     *
     * @param session - объект с текущей сессией на сервере
     */
    public void setNewFilterWhenSignOut(HttpSession session) {
        try {
            Filter newFilter = getCurrentFilter(session);
            newFilter.setOnlyMyMessage(false);
            if (newFilter.getAuthorName().equals(session.getAttribute(AppConst.USER_IN_SESSION))) {
                newFilter.setAuthorName("");
            }
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't set new filter after sign out", ex);
        }
    }

    /**
     * Устанавливает новый фильтр.
     *
     * @param session         - объект с текущей сессией на сервере
     * @param onlyMyMessage   - показывать только мои сообщения
     * @param authorName      - автор собщений
     * @param partOfTitle     - часть заголовка
     * @param partOfMessage   - часть сообщения
     * @param checkedCategory - список выбранных категорий
     * @return - строка с кодом успешного выполнения или сообщение об ошибке
     */
    public String setNewFilter(HttpSession session, boolean onlyMyMessage, String authorName, String partOfTitle,
                               String partOfMessage, String checkedCategory) {
        try {

            //set new filter
            Filter newFilter = new Filter(categoryService.getCategoryList());
            for (Category loopCategory : getCheckedCategoryList(checkedCategory)) {
                newFilter.getCategoryMap().put(loopCategory, true);
            }
            newFilter.setPartOfTitle(partOfTitle);
            newFilter.setPartOfMessage(partOfMessage);
            newFilter.setOnlyMyMessage(onlyMyMessage);
            newFilter.setAuthorName(authorName);

            //check field - author
            if (onlyMyMessage) {
                accountService.checkPermission(session);
                newFilter.setAuthorName((String) session.getAttribute(AppConst.USER_IN_SESSION));
            } else {
                if (!newFilter.getAuthorName().equals("")) {
                    if (accountService.getCountAccountsByLogin(newFilter.getAuthorName()) == 0) {
                        return "Author " + newFilter.getAuthorName() + " does not exist.";
                    }
                }
            }

            // validate filter
            Set<ConstraintViolation<Filter>> constraintViolations = validator.validate(newFilter);
            if (constraintViolations.size() > 0) {
                String validationResult = constraintViolations.iterator().next().getMessage();
                AppLog.userInfo(LOGGER_NAME, session, "Set filter failed - " + validationResult);
                return validationResult;
            }

            // set filter in session
            session.setAttribute(AppConst.FILTER_IN_SESSION, newFilter);
            return AppConst.OK_RESPONSE;

        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't set new filter", ex);
        }
    }

    private List<Category> getCheckedCategoryList(String checkedCategory) {
        List<Category> resultList = new ArrayList<>();
        for (String loopString : checkedCategory.split(",")) {
            if (loopString.equals("")) {
                continue;
            }
            resultList.add(categoryService.getCategoryById(Integer.parseInt(loopString)));
        }
        return resultList;
    }
}