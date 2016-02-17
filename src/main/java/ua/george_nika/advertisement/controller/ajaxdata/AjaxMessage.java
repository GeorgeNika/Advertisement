/**
 * Класс служит для создания копии объекта Message,
 * который будет передаваться в ответе на запрос
 * Created by george on 15.02.2016.
 */

package ua.george_nika.advertisement.controller.ajaxdata;

import org.joda.time.format.DateTimeFormat;
import ua.george_nika.advertisement.model.Message;
import ua.george_nika.advertisement.util.AppConst;

@SuppressWarnings("unused")

public class AjaxMessage {

    private int idMessage;
    private String authorName;
    private int idCategory;
    private String categoryName;
    private String title;
    private String message;
    private String created;
    private String updated;

    public AjaxMessage(Message message) {
        this.idMessage = message.getIdMessage();
        this.authorName = message.getAccount().getLogin();
        this.idCategory = message.getCategory().getIdCategory();
        this.categoryName = message.getCategory().getName();
        this.title = message.getTitle();
        this.message = message.getMessage();
        this.created = message.getCreated().toString(DateTimeFormat.forPattern(AppConst.DATE_TIME_FORMAT));
        this.updated = message.getUpdated().toString(DateTimeFormat.forPattern(AppConst.DATE_TIME_FORMAT));
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
