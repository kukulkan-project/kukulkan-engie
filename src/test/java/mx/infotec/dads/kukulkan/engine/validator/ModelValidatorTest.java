package mx.infotec.dads.kukulkan.engine.validator;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.infotec.dads.kukulkan.engine.service.DefaultModelValidator;

@RunWith(Parameterized.class)
public class ModelValidatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelValidatorTest.class);

    @Parameter(0)
    public String tesis;

    @Parameter(1)
    public String antiTesis;

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { "Persona", "persona" }, { "PersonaDireccion", "personaDireccionP" } };
        return Arrays.asList(data);
    }

    @Test
    public void testValidateModel() {
        Arrays.asList("Persona", "PersonaDireccion").forEach(word -> {
            LOGGER.info("Testing ValidateModel with param {}", word);
            Assert.assertTrue(DefaultModelValidator.ENTITY_NAME_PATTERN.matcher(word).find());
        });

        Arrays.asList("persona", "personaDireccion", "direccionEnn").forEach(word -> {
            LOGGER.info("Testing ValidateModel with param {}", word);
            Assert.assertTrue(DefaultModelValidator.PROPERTY_NAME_PATTERN.matcher(word).find());
        });

        Arrays.asList("persona", "personaDireccion", "persona Direccion").forEach(word -> {
            LOGGER.info("Testing ValidateModel with param {}", word);
            Assert.assertFalse(DefaultModelValidator.ENTITY_NAME_PATTERN.matcher(word).find());
        });

        Arrays.asList("personaDireccionD", "Persona").forEach(word -> {
            LOGGER.info("Testing ValidateModel with param {}", word);
            Assert.assertFalse(DefaultModelValidator.PROPERTY_NAME_PATTERN.matcher(word).find());
        });
    }
}
