package com.ivitera.velocity.validator.Validators;

import com.ivitera.velocity.validator.exceptions.InitializationException;

import java.io.File;

public interface Validator {
   void validate(String filename) throws Exception;
   void validate(File file) throws Exception;
   void init(File config) throws InitializationException;
}
