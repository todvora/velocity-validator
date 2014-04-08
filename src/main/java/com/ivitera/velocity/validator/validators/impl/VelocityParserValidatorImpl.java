package com.ivitera.velocity.validator.validators.impl;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import com.ivitera.velocity.validator.validators.Validator;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VelocityParserValidatorImpl implements Validator {

    public void validate(File file) throws ValidationException, IOException {
        try (
                BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
        ) {
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}