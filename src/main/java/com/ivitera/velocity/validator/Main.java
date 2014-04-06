package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.validators.Validator;
import com.ivitera.velocity.validator.validators.ValidatorsService;
import com.ivitera.velocity.validator.exceptions.InitializationException;
import com.ivitera.velocity.validator.utils.PathSearcher;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.List;

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
        if (args.length < 1 || args.length > 3) {
            printUsage();
            System.exit(1);
        }

        String baseDir = null;
        boolean verbose = false;
        String additionalRulesFile = null;

        for(String arg : args) {
            if("-verbose".equals(arg)) {
                verbose = true;
                log.info("Verbose mode on");
            }

            if(arg.startsWith("-rules=")) {
                additionalRulesFile = arg.replace("-rules=", "");
            }

            if(!arg.startsWith("-")) {
                baseDir = arg;
            }
        }


        if(baseDir == null) {
            baseDir = System.getProperty("user.dir");
        }

        if(!baseDir.endsWith("/")) {
            baseDir = baseDir + "/";
        }

        List<File> files = PathSearcher.getFileListing(new File(baseDir), new FileFilter() {
            public boolean accept(File file) {
                return file.getAbsolutePath().endsWith(".vm");
            }
        });

        try {
            ValidatorsService.init(additionalRulesFile != null ? new File(additionalRulesFile) : null);
        } catch (Exception e) {
            throw new InitializationException(e);
        }


        List<? extends Validator> validators = ValidatorsService.getAllValidators();
        int errors = 0;
        for (Validator validator : validators) {
            for (File f : files) {
                try {
                    validator.validate(f);
                    if(verbose) {
                        log.info("File OK: " + f.getAbsolutePath().replace(baseDir, "./"));
                    }
                } catch (Exception e) {
                    errors++;
                    log.error("Error in file " + f.getAbsolutePath().replace(baseDir, "./"));
                    log.error("    " + e.getMessage().replace(baseDir, "./").replace("\n", "\n    "));
                }
            }
        }
        if(verbose) {
            log.info("Checked " + files.size() + " files");
            if(errors == 0) {
                log.info("No errors found in given path");
            }
        }
        if (errors > 0) {
            log.error("Done, Found " + errors + " errors");
            System.exit(1);
        }
    }


    private static void printUsage() {
        log.info("Usage: java -jar velovalidator.jar path_to_templates [-rules=path_to_config_file] [-verbose]");
    }
}
