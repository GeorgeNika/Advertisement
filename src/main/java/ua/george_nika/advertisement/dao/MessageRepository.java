/**
 * Интерфейс служит для работы с данными таблицы,
 * связанными с классом Message.
 * Использует framework Spring Data.
 * Created by george on 12.02.2016.
 */

package ua.george_nika.advertisement.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Message;

import java.util.List;

@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Integer> {


    /**
     * Получает данные из таблицы Message с заданными ограничениями
     * и для указанной страницы из постраничного отображения.
     *
     * @param account      - автор сообщения (чтобы ограничения не включались нужно передать null)
     * @param categoryList - список категорий для ограничения (чтобы ограничения не включались нужно передать null)
     * @param title        - часть заголовка, которая должна быть (чтобы ограничения не включались нужно передать "")
     * @param message      - часть сообщения, которая должна быть (чтобы ограничения не включались нужно передать "")
     * @param pageable     - объект содержащий страницу и направление сортировки
     */
    @Query("select m from Message m " +
            "where ((m.account = :account) or (:account is null )) " +
            "and ((m.category in (:categoryList)) or ((:categoryList) is null)) " +
            "and (m.title like CONCAT('%', :title, '%'))  " +
            "and (m.message like CONCAT('%', :message, '%'))  " +
            "order by m.updated desc")
    List<Message> findByFilterAndPageable(
            @Param("account") Account account,
            @Param("categoryList") List<Category> categoryList,
            @Param("title") String title,
            @Param("message") String message,
            Pageable pageable);


    /**
     * Получает количество данных из таблицы Message с заданными ограничениями.
     *
     * @see MessageRepository#findByFilterAndPageable
     */

    @Query("select count(m) from Message m " +
            "where ((m.account = :account) or (:account is null )) " +
            "and ((m.category in (:categoryList)) or ((:categoryList) is null)) " +
            "and (m.title like CONCAT('%', :title, '%'))  " +
            "and (m.message like CONCAT('%', :message, '%'))")
    int getCountByFilter(
            @Param("account") Account account,
            @Param("categoryList") List<Category> categoryList,
            @Param("title") String title,
            @Param("message") String message);
}
