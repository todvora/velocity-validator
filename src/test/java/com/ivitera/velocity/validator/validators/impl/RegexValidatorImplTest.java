package com.ivitera.velocity.validator.validators.impl;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import com.ivitera.velocity.validator.validators.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class RegexValidatorImplTest {

    private Validator validator;

    @Before
    public void setup() throws InitializationException {
        validator = new RegexValidatorImpl();
        String filename = this.getClass().getResource("/properties.conf").getFile();
        validator.init(new File(filename));
    }

    @Test
    public void testCorrectTemplate() {
        String file = this.getClass().getResource("/CorrectTemplate.vm").getFile();
        try {
            validator.validate(new File(file));
        } catch (Exception e) {
            Assert.fail("Should not throw exception");
        }
    }

    @Test(expected = ValidationException.class)
    public void testWrongForeach() throws Exception {
        String file = this.getClass().getResource("/WrongForeachTemplate.vm").getFile();
        validator.validate(new File(file));
    }
}
