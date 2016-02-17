/**
 * Интерфейс служит для работы с данными таблицы,
 * связанными с классом Category.
 * Использует framework Spring Data.
 * Created by george on 12.02.2016.
 */

package ua.george_nika.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.george_nika.advertisement.model.Category;

@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
