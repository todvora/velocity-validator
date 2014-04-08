package com.ivitera.velocity.validator.validators.impl;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import com.ivitera.velocity.validator.validators.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class VelocityParserValidatorImplTest {

    private Validator validator;

    @Before
    public void setup() throws InitializationException {
        validator = new VelocityParserValidatorImpl();
        validator.init(null);
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
    public void testIncorrectVariableName() throws Exception {
        String file = this.getClass().getResource("/IncorrectVariableName.vm").getFile();
        validator.validate(new File(file));
    }

    @Test(expected = ValidationException.class)
    public void testTwoEndsBlock() throws Exception {
        String file = this.getClass().getResource("/TwoEndBlocksTemplate.vm").getFile();
        validator.validate(new File(file));
    }

    @Test(expected = ValidationException.class)
    public void testUnfinishedBlock() throws Exception {
        String file = this.getClass().getResource("/UnfinishedBlockTemplate.vm").getFile();
        validator.validate(new File(file));
    }
}
