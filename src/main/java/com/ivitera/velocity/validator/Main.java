package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.validators.Runner;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        try {
            doRun(args);
        } catch (Exception e) {
            log.error("Failed to run Velocity validator", e);
        }
    }

    private static void doRun(String[] args) throws FileNotFoundException, InitializationException {
        ParamsParser paramsParser = new ParamsParser(args).invoke();
        File configFile = paramsParser.getConfigFile();
        File baseDirFile = paramsParser.getBaseDirFile();
        boolean verbose = paramsParser.isVerbose();

        Runner runner = new Runner(configFile, baseDirFile, verbose);
        boolean hasErrors = runner.run();
        if(hasErrors) {
            System.exit(1);
        }
    }


}
