import org.junit.BeforeClass;
import org.junit.Test;
import ua.george_nika.advertisement.model.Account;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Filter;
import ua.george_nika.advertisement.service.CategoryService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by george on 17.02.2016.
 */
public class FilterValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void countTestedFields() {
        Filter filter = new Filter(new ArrayList<Category>());

        Set<ConstraintViolation<Filter>> constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfTitle("1");
        filter.setPartOfMessage("1");
        filter.setAuthorName("1");
        constraintViolations = validator.validate(filter);
        assertEquals(3, constraintViolations.size());
    }

    @Test
    public void checkAuthorNameField() {
        Filter filter = new Filter(new ArrayList<Category>());
        filter.setPartOfTitle("1234");
        filter.setPartOfMessage("1234");

        filter.setAuthorName("");
        Set<ConstraintViolation<Filter>> constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setAuthorName("1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("author size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        filter.setAuthorName("123");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("author size must be between 4 and 50", constraintViolations.iterator().next().getMessage());

        filter.setAuthorName("1234");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setAuthorName("123456789 123456789 123456789 123456789 1234567890");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setAuthorName("123456789 123456789 123456789 123456789 123456789 1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("author size must be between 4 and 50", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkPartOfTitleField() {
        Filter filter = new Filter(new ArrayList<Category>());
        filter.setAuthorName("1234");
        filter.setPartOfMessage("1234");

        filter.setPartOfTitle("");
        Set<ConstraintViolation<Filter>> constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfTitle("1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of title size must be between 4 and 20", constraintViolations.iterator().next().getMessage());

        filter.setPartOfTitle("123");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of title size must be between 4 and 20", constraintViolations.iterator().next().getMessage());

        filter.setPartOfTitle("1234");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfTitle("123456789 1234567890");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfTitle("123456789 123456789 1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of title size must be between 4 and 20", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkPartOfMessageField() {
        Filter filter = new Filter(new ArrayList<Category>());
        filter.setAuthorName("1234");
        filter.setPartOfTitle("1234");

        filter.setPartOfMessage("");
        Set<ConstraintViolation<Filter>> constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfMessage("1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of message size must be between 4 and 20", constraintViolations.iterator().next().getMessage());

        filter.setPartOfMessage("123");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of message size must be between 4 and 20", constraintViolations.iterator().next().getMessage());

        filter.setPartOfMessage("1234");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfMessage("123456789 1234567890");
        constraintViolations = validator.validate(filter);
        assertEquals(0, constraintViolations.size());

        filter.setPartOfMessage("123456789 123456789 1");
        constraintViolations = validator.validate(filter);
        assertEquals(1, constraintViolations.size());
        assertEquals("part of message size must be between 4 and 20", constraintViolations.iterator().next().getMessage());
    }

}
