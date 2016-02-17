/**
 * Класс служит для создания объекта,
 * который будет передаваться в ответе на запрос.
 * В объекте находится текущая страница и список сообщений
 * Created by george on 15.02.2016.
 */

package ua.george_nika.advertisement.controller.ajaxdata;

import java.util.List;

@SuppressWarnings("unused")

public class AjaxMessagePage {

    /**
     * Свойство - текущая страница при постраничном выводе
     */
    private Integer page;

    /**
     * Свойство - список копий сообщений для передачи в ответе на запрос
     */
    private List<AjaxMessage> ajaxMessageList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<AjaxMessage> getAjaxMessageList() {
        return ajaxMessageList;
    }

    public void setAjaxMessageList(List<AjaxMessage> ajaxMessageList) {
        this.ajaxMessageList = ajaxMessageList;
    }
}
