/**
 * Объект для хранения информации о фильтре
 * Использует Hibernate validator.
 * Created by george on 16.02.2016.
 */

package ua.george_nika.advertisement.model;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")

public class Filter {

    /**
     * Свойство - показывать только мои сообщения
     */
    private boolean onlyMyMessage = false;

    @Pattern(regexp = "(^$|.{4,50})", message = "author size must be between 4 and 50")
    private String authorName = "";

    @Pattern(regexp = "(^$|.{4,20})", message = "part of title size must be between 4 and 20")
    private String partOfTitle = "";

    @Pattern(regexp = "(^$|.{4,20})", message = "part of message size must be between 4 and 20")
    private String partOfMessage = "";

    /**
     * Свойство - для указания какие категории выбраны, а какие нет.
     */
    private Map<Category, Boolean> categoryMap = new HashMap<>();


    /**
     * Метод - создает новый фильтр
     *
     * @deprecated — данные с категориями остаются не заполненными
     */
    public Filter() {
    }

    /**
     * Метод - создает новый фильтр
     *
     * @param categoryList — данные с категориями
     */
    public Filter(List<Category> categoryList) {
        for (Category loopCategory : categoryList) {
            categoryMap.put(loopCategory, false);
        }
    }

    public boolean isOnlyMyMessage() {
        return onlyMyMessage;
    }

    public void setOnlyMyMessage(boolean onlyMyMessage) {
        this.onlyMyMessage = onlyMyMessage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPartOfTitle() {
        return partOfTitle;
    }

    public void setPartOfTitle(String partOfTitle) {
        this.partOfTitle = partOfTitle;
    }

    public String getPartOfMessage() {
        return partOfMessage;
    }

    public void setPartOfMessage(String partOfMessage) {
        this.partOfMessage = partOfMessage;
    }

    public Map<Category, Boolean> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Category, Boolean> categoryMap) {
        this.categoryMap = categoryMap;
    }
}
