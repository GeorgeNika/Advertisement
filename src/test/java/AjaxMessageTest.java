import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import ua.george_nika.advertisement.controller.ajaxdata.AjaxMessage;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Message;
import ua.george_nika.advertisement.util.AppConst;

import static org.junit.Assert.assertEquals;

/**
 * Created by george on 17.02.2016.
 */
public class AjaxMessageTest {

    @Test
    public void createAjaxMessageTest(){
        Account account = new Account("aaaaa", "ppppp");
        Category category = new Category();
        category.setIdCategory(5);
        category.setName("ccccc");

        Message message = new Message();
        message.setAccount(account);
        message.setCategory(category);
        message.setMessage("123456789 123456789 1234567890");
        message.setTitle("123456789 1234567890");
        message.setIdMessage(15);
        message.setCreated(new LocalDateTime());
        message.setUpdated(new LocalDateTime());

        AjaxMessage ajaxMessage = new AjaxMessage(message);

        assertEquals(ajaxMessage.getAuthorName(), message.getAccount().getLogin());
        assertEquals(ajaxMessage.getCategoryName(), message.getCategory().getName());
        assertEquals(ajaxMessage.getIdCategory(), message.getCategory().getIdCategory());
        assertEquals(ajaxMessage.getMessage(), message.getMessage());
        assertEquals(ajaxMessage.getTitle(), message.getTitle());
        assertEquals(ajaxMessage.getCreated(), message.getCreated().toString(DateTimeFormat.forPattern(AppConst.DATE_TIME_FORMAT)));
        assertEquals(ajaxMessage.getUpdated(), message.getUpdated().toString(DateTimeFormat.forPattern(AppConst.DATE_TIME_FORMAT)));
    }
}
