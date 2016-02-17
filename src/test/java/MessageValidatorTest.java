import org.junit.BeforeClass;
import org.junit.Test;
import ua.george_nika.advertisement.model.Category;
import ua.george_nika.advertisement.model.Filter;
import ua.george_nika.advertisement.model.Message;

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
public class MessageValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void countTestedFields() {
        Message message = new Message();

        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(message);
        assertEquals(2, constraintViolations.size());

        message.setTitle("123456789 1");
        message.setMessage("123456789 123456789 1");
        constraintViolations = validator.validate(message);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void checkTitleField() {
        Message message = new Message();
        message.setMessage("123456789 123456789 1");

        message.setTitle("");
        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());

        message.setTitle("1");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("title size must be between 10 and 30", constraintViolations.iterator().next().getMessage());

        message.setTitle("123456789");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("title size must be between 10 and 30", constraintViolations.iterator().next().getMessage());

        message.setTitle("1234567890");
        constraintViolations = validator.validate(message);
        assertEquals(0, constraintViolations.size());

        message.setTitle("123456789 123456789 1234567890");
        constraintViolations = validator.validate(message);
        assertEquals(0, constraintViolations.size());

        message.setTitle("123456789 123456789 123456789 1");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("title size must be between 10 and 30", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void checkMessageField() {
        Message message = new Message();
        message.setTitle("1234567890");

        message.setMessage("");
        Set<ConstraintViolation<Message>> constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());

        message.setMessage("1");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("message size must be between 20 and 400", constraintViolations.iterator().next().getMessage());

        message.setMessage("123456789 123456789");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("message size must be between 20 and 400", constraintViolations.iterator().next().getMessage());

        message.setMessage("123456789 1234567890");
        constraintViolations = validator.validate(message);
        assertEquals(0, constraintViolations.size());

        message.setMessage("123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 1234567890");
        constraintViolations = validator.validate(message);
        assertEquals(0, constraintViolations.size());

        message.setMessage("123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 "+
        "1");
        constraintViolations = validator.validate(message);
        assertEquals(1, constraintViolations.size());
        assertEquals("message size must be between 20 and 400", constraintViolations.iterator().next().getMessage());
    }

}
