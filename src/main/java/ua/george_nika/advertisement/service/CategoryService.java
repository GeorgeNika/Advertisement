/**
 * Класс служит для работы с объектом Category.
 * Использует framework Spring MVC.
 * Created by george on 12.02.2016.
 */

package ua.george_nika.advertisement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.george_nika.advertisement.dao.CategoryRepository;
import ua.george_nika.advertisement.error.ApplicationError;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.util.AppLog;

import java.util.List;

@Service
public class CategoryService {

    private static String LOGGER_NAME = AppLog.SERVICE;
    private static String CLASS_NAME = CategoryService.class.getName();

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Возвращает объект Category по номеру
     *
     * @param idCategory - номер искомого Category
     */
    public Category getCategoryById(int idCategory) {
        try {
            return categoryRepository.findOne(idCategory);
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get category with id - " + idCategory, ex);
        }
    }

    /**
     * Возвращает список всех объектов Category
     */
    public List<Category> getCategoryList() {
        try {
            return categoryRepository.findAll();
        } catch (RuntimeException ex) {
            throw new ApplicationError(LOGGER_NAME, CLASS_NAME, "Can't get all categories", ex);
        }
    }

}
