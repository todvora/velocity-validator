package com.ivitera.velocity.validator.validators;

import com.ivitera.velocity.validator.validators.impl.RegexValidatorImpl;
import com.ivitera.velocity.validator.validators.impl.VelocityParserValidatorImpl;
import com.ivitera.velocity.validator.utils.Lists;

import java.io.File;
import java.util.List;

public class ValidatorsService {

    private static boolean initialized = false;

    private static final List<? extends Validator> ALL_VALIDATORS = Lists.arrayList(
            new VelocityParserValidatorImpl(),
            new RegexValidatorImpl()
    );


    public static void init(File config) throws Exception {
        for(Validator v : ALL_VALIDATORS) {
            v.init(config);
        }
        initialized = true;
    }


    public static List<? extends Validator> getAllValidators(){
        if(!initialized) {
            throw new RuntimeException("Service not initialized!");
        }
        return ALL_VALIDATORS;
    }
}
