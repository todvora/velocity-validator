package com.ivitera.velocity.validator.Validators;

import com.ivitera.velocity.validator.Validators.impl.RegexValidatorImpl;
import com.ivitera.velocity.validator.Validators.impl.VelocityParserValidatorImpl;
import com.ivitera.velocity.validator.utils.Lists;

import java.io.File;
import java.util.List;

public class ValidatorsService {

    private static boolean initialized = false;

    private static final List<Validator> ALL_VALIDATORS = Lists.arrayList();

    static {
        ALL_VALIDATORS.add(new VelocityParserValidatorImpl());
        ALL_VALIDATORS.add(new RegexValidatorImpl());
    }

    public static void init(File config) throws Exception {
        for(Validator v : ALL_VALIDATORS) {
            v.init(config);
        }
        initialized = true;
    }


    public static List<Validator> getAllValidators(){
        if(!initialized) {
            throw new RuntimeException("Service not initialized!");
        }
        return ALL_VALIDATORS;
    }
}
