import org.junit.BeforeClass;
import org.junit.Test;
import ua.george_nika.advertisement.model.Account;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by george on 17.02.2016.
 */
public class AccountValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void countTestedFields() {
        Account account = new Account();

        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        assertEquals(2, constraintViolations.size());

        account.setLogin("12345");
        account.setPassword("12345");
        constraintViolations = validator.validate(account);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void checkLoginField() {
        Account account = new Account(null, "12345");
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("login must be not empty", constraintViolations.iterator().next().getMessage());

        account = new Account("1", "12345");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("login size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        account = new Account("123", "12345");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("login size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        account = new Account("1234", "12345");
        constraintViolations = validator.validate(account);
        assertEquals(0, constraintViolations.size());

        account = new Account("123456789 123456789 123456789 123456789 1234567890", "12345");
        constraintViolations = validator.validate(account);
        assertEquals(0, constraintViolations.size());

        account = new Account("123456789 123456789 123456789 123456789 1123456789 1", "12345");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("login size must be between 4 and 50", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkPasswordField() {
        Account account = new Account("12345", null);
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("password must be not empty", constraintViolations.iterator().next().getMessage());

        account = new Account("12345", "1");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("password size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        account = new Account("12345", "123");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("password size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        account = new Account("12345", "1234");
        constraintViolations = validator.validate(account);
        assertEquals(0, constraintViolations.size());

        account = new Account("12345", "123456789 123456789 123456789 123456789 1234567890");
        constraintViolations = validator.validate(account);
        assertEquals(0, constraintViolations.size());

        account = new Account("12345", "123456789 123456789 123456789 123456789 1123456789 1");
        constraintViolations = validator.validate(account);
        assertEquals(1, constraintViolations.size());
        assertEquals("password size must be between 4 and 50", constraintViolations.iterator().next().getMessage());
    }
}
