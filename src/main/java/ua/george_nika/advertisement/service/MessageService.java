/**
 * Класс служит для работы с объектом Message.
 * Использует framework Spring MVC.
 * Created by george on 12.02.2016.
 */

package ua.george_nika.advertisement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.george_nika.advertisement.controller.ajaxdata.AjaxMessage;
import ua.george_nika.advertisement.controller.ajaxdata.AjaxMessagePage;
import ua.george_nika.advertisement.dao.MessageRepository;
import ua.george_nika.advertisement.error.ApplicationError;
import ua.george_nika.advertisement.model.Filter;
import ua.george_nika.advertisement.model.Message;
import ua.george_nika.advertisement.util.AppConst;
import ua.george_nika.advertisement.util.AppLog;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MessageService {

    private static String LOGGER_NAME = AppLog.SERVICE;
    private static String CLASS_NAME = MessageService.class.getName();

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    FilterService filterService;

    @Autowired
    Validator validator;

    /**
     * Возвращает объект Message по номеру.
     *
     * @param idMessage - номер искомого Message
     */
    public Message getMessageById(int idMessage) {
        try {
            return messageRepository.findOne(idMessage);
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get message by id - " + idMessage, ex);
        }
    }

    /**
     * Сохраняем объект Message с полученными данными.
     *
     * @param session     - объект текущей сессии
     * @param idMessage   - номер записуемого Message (0 - если новое)
     * @param category    - номер категории
     * @param title       - заголовок
     * @param messageBody - сообщение
     * @return - строка с кодом успешного выполнения или сообщение об ошибке
     */
    public String saveMessage(HttpSession session, int idMessage, int category, String title, String messageBody) {
        try {
            // fill message
            Message message;
            if (idMessage == 0) {
                message = new Message();
                message.setAccount(accountService.getAccountByName((String) session.getAttribute(AppConst.USER_IN_SESSION)));
            } else {
                message = messageRepository.findOne(idMessage);
            }
            message.setCategory(categoryService.getCategoryById(category));
            message.setTitle(title);
            message.setMessage(messageBody);

            // validate account
            Set<ConstraintViolation<Message>> constraintViolations = validator.validate(message);
            if (constraintViolations.size() > 0) {
                String validationResult = constraintViolations.iterator().next().getMessage();
                AppLog.userInfo(LOGGER_NAME, session, "Save failed - " + validationResult);
                return validationResult;
            }

            // check for author
            if (message.getAccount().getLogin().equals(session.getAttribute(AppConst.USER_IN_SESSION))) {
                messageRepository.saveAndFlush(message);
                return AppConst.OK_RESPONSE;
            } else {
                return "Can't save message. Wrong author.";
            }
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't set message", ex);
        }
    }

    /**
     * Удаляет объект Message из базы.
     *
     * @param session   - объект текущей сессии
     * @param idMessage - номер удаляемого Message
     * @return - строка с кодом успешного выполнения или сообщение об ошибке
     */
    public String deleteMessage(HttpSession session, int idMessage) {
        try {
            Message message = messageRepository.findOne(idMessage);
            if (message.getAccount().getLogin().equals(session.getAttribute(AppConst.USER_IN_SESSION))) {
                messageRepository.delete(idMessage);
                return AppConst.OK_RESPONSE;
            } else {
                return "Can't delete message. Wrong author.";
            }
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't delete message with id - " + idMessage, ex);
        }
    }

    /**
     * Возвращаят следующую страницу для отображения.
     *
     * @param session       - объект текущей сессии
     * @param pageDirection - направление следующей страницы (вперед, назад, в конец, в начало)
     * @return - объект для отправки в ответе на запрос
     */
    public AjaxMessagePage getNextAjaxMessagePage(HttpSession session, String pageDirection) {
        try {
            AjaxMessagePage resultMessagePage = new AjaxMessagePage();
            Filter filter = filterService.getCurrentFilter(session);
            resultMessagePage.setPage(getNextPage(session, pageDirection, filter));
            List<AjaxMessage> resultList = new ArrayList<>();
            for (Message loopMsg : getMessageListForPage(resultMessagePage.getPage(), filter)) {
                resultList.add(new AjaxMessage(loopMsg));
            }
            resultMessagePage.setAjaxMessageList(resultList);

            return resultMessagePage;

        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get page with direction - " + pageDirection, ex);
        }
    }

    private List<Message> getMessageListForPage(int page, Filter filter) {

        return messageRepository.findByFilterAndPageable(
                filterService.getFilteredAccount(filter),
                filterService.getFilteredCategoryList(filter),
                filter.getPartOfTitle(),
                filter.getPartOfMessage(),
                new PageRequest(page - 1, AppConst.MESSAGE_ON_PAGE)
        );
    }

    private int getNextPage(HttpSession session, String pageDirection, Filter filter) {
        int resultPage;
        int prevPage;

        if (session.getAttribute(AppConst.PAGE_IN_SESSION) == null) {
            prevPage = 1;
        } else {
            prevPage = (Integer) session.getAttribute(AppConst.PAGE_IN_SESSION);
        }
        resultPage = prevPage;
        if (!pageDirection.isEmpty()) {
            switch (pageDirection) {
                case "next":
                    if (resultPage < getLastPageWithFilter(filter)) {
                        resultPage = resultPage + 1;
                    }
                    break;
                case "prev":
                    if (resultPage > 1) {
                        resultPage = prevPage - 1;
                    }
                    break;
                case "start":
                    resultPage = 1;
                    break;
                case "end":
                    resultPage = getLastPageWithFilter(filter);
                    break;
            }
        }

        return resultPage;
    }

    private int getLastPageWithFilter(Filter filter) {
        return 1 + (messageRepository.getCountByFilter(
                filterService.getFilteredAccount(filter),
                filterService.getFilteredCategoryList(filter),
                filter.getPartOfTitle(),
                filter.getPartOfMessage()) - 1) / AppConst.MESSAGE_ON_PAGE;
    }
}
