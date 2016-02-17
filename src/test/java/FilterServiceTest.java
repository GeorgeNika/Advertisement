import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Filter;
import ua.george_nika.advertisement.service.CategoryService;
import ua.george_nika.advertisement.service.FilterService;
import ua.george_nika.advertisement.util.AppConst;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by george on 17.02.2016.
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
@WebAppConfiguration
public class FilterServiceTest {

    private Filter filter;
    private Category category;

    @Resource
    private FilterService filterService;

    @Autowired
    MockHttpSession session;

    @Autowired
    CategoryService categoryService;


    @Before
    public void setCategory() {
        category = categoryService.getCategoryById(1);
    }

    @Before
    public void setFilter() {
        filter = new Filter(categoryService.getCategoryList());
        filter.setAuthorName("Admin");
        filter.setPartOfTitle("агол");
        filter.setPartOfMessage("проба");
        Map<Category, Boolean> tempMap = filter.getCategoryMap();
        tempMap.put(category, true);
        filter.setCategoryMap(tempMap);
    }

    @Test
    public void getCurrentFilterTest() {
        assertNotEquals(filter, filterService.getCurrentFilter(session));

        session.setAttribute(AppConst.FILTER_IN_SESSION, filter);
        assertEquals(filter, filterService.getCurrentFilter(session));
    }

    @Test
    public void getFilteredAccountTest() {
        assertEquals(filter.getAuthorName(), filterService.getFilteredAccount(filter).getLogin());
        assertNotEquals("no Admin", filterService.getFilteredAccount(filter).getLogin());
    }

    @Test
    public void getFilteredCategoryListTest() {
        assertEquals(1, filterService.getFilteredCategoryList(filter).size());
        assertEquals(category, filterService.getFilteredCategoryList(filter).get(0));
    }

    @Test
    public void setNewFilterTest() {
        filterService.setNewFilter(session, filter.isOnlyMyMessage(), filter.getAuthorName(), filter.getPartOfTitle(),
                filter.getPartOfMessage(), "" + filterService.getFilteredCategoryList(filter).get(0).getIdCategory());
        assertEquals(filter.isOnlyMyMessage(), filterService.getCurrentFilter(session).isOnlyMyMessage());
        assertEquals(filter.getAuthorName(), filterService.getCurrentFilter(session).getAuthorName());
        assertEquals(filter.getPartOfTitle(), filterService.getCurrentFilter(session).getPartOfTitle());
        assertEquals(filter.getPartOfMessage(), filterService.getCurrentFilter(session).getPartOfMessage());
        assertEquals(filter.getCategoryMap().size(), filterService.getCurrentFilter(session).getCategoryMap().size());
    }

    @Test
    public void setNewFilterWhenSignOutTest() {

        //todo
    }
}
