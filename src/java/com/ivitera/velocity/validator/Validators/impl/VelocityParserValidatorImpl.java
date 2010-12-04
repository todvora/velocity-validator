package com.ivitera.velocity.validator.Validators.impl;

import com.ivitera.velocity.validator.Validators.Validator;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import org.apache.log4j.Logger;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

import java.io.*;

public enum VelocityParserValidatorImpl implements Validator {
    INSTANCE;

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

    public void init(File config) {
        try {
            RuntimeSingleton.init();
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).error(e);
        }
    }
}