package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.exceptions.InputParamsException;
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
        ParamsParser paramsParser = null;
        try {
            paramsParser = new ParamsParser(args).parse();
        } catch (InputParamsException e) {
            log.error("Usage: java -jar velocity-validator-1.0.jar path_to_templates [-rules=path_to_config_file] [-verbose]");
            System.exit(1);
        }
        File configFile = paramsParser.getConfigFile();
        File baseDirFile = paramsParser.getBaseDirFile();
        boolean verbose = paramsParser.isVerbose();

        Runner runner = new Runner(configFile, baseDirFile, verbose);
        boolean allFilesOk = runner.run();
        if(!allFilesOk) {
            // return code indicates, that there is incorrect template (uses CI server for test result)
            System.exit(1);
        }
    }


}
