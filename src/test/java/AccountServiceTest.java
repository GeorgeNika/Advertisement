import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.george_nika.advertisement.error.AccessDeniedException;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.service.AccountService;
import ua.george_nika.advertisement.util.AppConst;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by george on 17.02.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
@WebAppConfiguration
public class AccountServiceTest {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private AccountService accountService;

    @Autowired
    MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void getAccountByNameTest() {
        String name = "Admin";
        assertNotEquals(null, accountService.getAccountByName(name));
        assertEquals(name, accountService.getAccountByName(name).getLogin());

        name = "No Admin";
        assertEquals(null, accountService.getAccountByName(name));
    }

    @Test
    public void getCountAccountsByLoginTest() {
        String name = "Admin";
        assertEquals(1, accountService.getCountAccountsByLogin(name));

        name = "No Admin";
        assertEquals(0, accountService.getCountAccountsByLogin(name));
    }


    @Test
    public void signInActionTest() {
        // todo
        assertEquals("incorrect login or password", accountService.signInAction(new Account("Admin", "12345"), session));
        assertEquals("incorrect login or password", accountService.signInAction(new Account("No Admin", "12345"), session));
        assertEquals("incorrect login or password", accountService.signInAction(new Account("No Admin", "1234"), session));
        assertEquals(AppConst.OK_RESPONSE, accountService.signInAction(new Account("Admin", "1234"), session));
    }

    @Test
    public void joinActionTest() {
        // todo
        assertEquals("Sorry. Login already exist. Try another login.",
                accountService.joinAction(new Account("Admin", "1234"), session));

    }

    @Test(expected = AccessDeniedException.class)
    public void checkPermissionTest() {
        // todo
        accountService.checkPermission(session);
    }
}
