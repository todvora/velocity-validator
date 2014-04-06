package com.ivitera.velocity.validator.validators;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.ValidationException;

import java.io.File;
import java.io.IOException;

public interface Validator {
   void validate(String filename) throws ValidationException, IOException;
   void validate(File file) throws ValidationException, IOException;
   void init(File config) throws InitializationException;
   boolean isEnabled();
}
