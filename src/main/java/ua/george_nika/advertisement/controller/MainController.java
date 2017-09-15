/**
 * Класс служит для обработки общих запросов.
 * Использует framework Spring MVC.
 * Created by george on 10.02.2016.
 */

package ua.george_nika.advertisement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.george_nika.advertisement.controller.ajaxdata.AjaxMessage;
import ua.george_nika.advertisement.controller.ajaxdata.AjaxMessagePage;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.service.CategoryService;
import ua.george_nika.advertisement.service.FilterService;
import ua.george_nika.advertisement.service.MessageService;
import ua.george_nika.advertisement.service.AccountService;
import ua.george_nika.advertisement.util.AppConst;
import ua.george_nika.advertisement.util.AppLog;
import ua.george_nika.advertisement.util.HandleReCaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SuppressWarnings({"unused", "FieldCanBeLocal"})

@Controller
public class MainController {

    private static String LOGGER_NAME = AppLog.CONTROLLER;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MessageService messageService;

    @Autowired
    FilterService filterService;

    @Value("${captcha.private}")
    private String captchaPrivate;

    @Value("${captcha.public}")
    private String captchaPublic;

    @RequestMapping("/advertisement")
    public String advertisement(HttpServletRequest request, HttpSession session, Model model) {

        if (session.getAttribute(AppConst.SIGNIN_IN_SESSION) == null) {
            session.setAttribute(AppConst.SIGNIN_IN_SESSION, false);
            session.setAttribute(AppConst.USER_IN_SESSION, "");
        }
        AppLog.userInfo(LOGGER_NAME, session, "Go to Main Page");
        model.addAttribute(AppConst.FILTER_IN_SESSION, filterService.getCurrentFilter(session));
        model.addAttribute("captchaPublic", captchaPublic);
        model.addAttribute("okResponse", AppConst.OK_RESPONSE);

        return "advertisement";
    }

    @RequestMapping("/ajax/signInAction")
    @ResponseBody
    public String signInAction(HttpServletRequest request, HttpSession session, Model model,
                               @RequestParam(value = "login") String login,
                               @RequestParam(value = "password") String password) {

        Account account = new Account(login.trim(), password.trim());

        return accountService.signInAction(account, session);
    }

    @RequestMapping("/ajax/signOutAction")
    @ResponseBody
    public String signOutAction(HttpServletRequest request, HttpSession session, Model model) {
        AppLog.userInfo(LOGGER_NAME, session, "Sign Out");
        filterService.setNewFilterWhenSignOut(session);
        session.setAttribute(AppConst.SIGNIN_IN_SESSION, false);
        session.setAttribute(AppConst.USER_IN_SESSION, "");
        return AppConst.OK_RESPONSE;
    }

    @RequestMapping("/ajax/joinAction")
    @ResponseBody
    public String joinAction(HttpServletRequest request, HttpSession session, Model model,
                             @RequestParam(value = "login") String login,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "reCaptchaData") String reCaptchaData) {

//        if (!HandleReCaptcha.isGoodCaptcha(request, reCaptchaData, captchaPrivate)) {
//            return "captcha not valid";
//        }

        Account account = new Account(login.trim(), password.trim());

        return accountService.joinAction(account, session);
    }

    @RequestMapping("/ajax/getMessagePage")
    @ResponseBody
    public AjaxMessagePage getMessagePage(HttpServletRequest request, HttpSession session, Model model,
                                          @RequestParam(value = "pageDirection") String pageDirection) {

        AjaxMessagePage resultMessagePage = messageService.getNextAjaxMessagePage(session, pageDirection);
        session.setAttribute(AppConst.PAGE_IN_SESSION, resultMessagePage.getPage());
        return resultMessagePage;
    }

    @RequestMapping("/ajax/getMessage")
    @ResponseBody
    public AjaxMessage getMessage(HttpServletRequest request, HttpSession session, Model model,
                                  @RequestParam(value = "idMessage") int idMessage) {

        return new AjaxMessage(messageService.getMessageById(idMessage));
    }

    @RequestMapping("/ajax/setFilter")
    @ResponseBody
    public String setFilter(HttpServletRequest request, HttpSession session, Model model,
                            @RequestParam(value = "onlyMyMessage") Boolean onlyMyMessage,
                            @RequestParam(value = "authorName") String authorName,
                            @RequestParam(value = "partOfTitle") String partOfTitle,
                            @RequestParam(value = "partOfMessage") String partOfMessage,
                            @RequestParam(value = "checkedCategory") String checkedCategory) {

        return filterService.setNewFilter(session, onlyMyMessage, authorName, partOfTitle,
                partOfMessage, checkedCategory);
    }

    @RequestMapping("/ajax/saveMessage")
    @ResponseBody
    public String saveMessage(HttpServletRequest request, HttpSession session, Model model,
                              @RequestParam(value = "idMessage") int idMessage,
                              @RequestParam(value = "category") int category,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "messageBody") String messageBody) {

        accountService.checkPermission(session);
        return messageService.saveMessage(session, idMessage, category, title, messageBody);
    }

    @RequestMapping("/ajax/deleteMessage")
    @ResponseBody
    public String deleteMessage(HttpServletRequest request, HttpSession session, Model model,
                                @RequestParam(value = "idMessage") int idMessage) {

        accountService.checkPermission(session);
        return messageService.deleteMessage(session, idMessage);
    }
}
