package com.ivitera.velocity.validator.validators.impl;

import com.ivitera.velocity.validator.validators.Validator;
import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

import java.io.*;

public class VelocityParserValidatorImpl implements Validator {

    public void validate(String filename) throws ValidationException, FileNotFoundException  {
        validate(new File(filename));
    }

    public void validate(File file) throws ValidationException, FileNotFoundException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            RuntimeSingleton.getRuntimeServices().parse(br, file.getAbsolutePath());
        } catch (ParseException e) {
            throw new ValidationException(e);
        }
    }

    public void init(File config) throws InitializationException {
        try {
            RuntimeSingleton.init();
        } catch (Exception e) {
            throw new InitializationException("Failed to init VelocityParserValidator", e);
        }
    }
}