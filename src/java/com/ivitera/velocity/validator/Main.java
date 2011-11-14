package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.Validators.Validator;
import com.ivitera.velocity.validator.Validators.ValidatorsService;
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
        if (args.length < 1 || args.length > 2) {
            printUsage();
            System.exit(1);
        }

        String baseDir = args[0];
        if(!baseDir.endsWith("/")) {
            baseDir = baseDir + "/";
        }

        List<File> files = PathSearcher.getFileListing(new File(baseDir), new FileFilter() {
            public boolean accept(File file) {
                return file.getAbsolutePath().endsWith(".vm");
            }
        });

        try {
            ValidatorsService.init(args.length > 1 ? new File(args[1]) : null);
        } catch (Exception e) {
            throw new InitializationException(e);
        }


        List<? extends Validator> validators = ValidatorsService.getAllValidators();
        int errors = 0;
        for (Validator validator : validators) {
            for (File f : files) {
                try {
                    validator.validate(f);
                } catch (Exception e) {
                    errors++;
                    System.err.println("Error in file " + f.getAbsolutePath().replace(baseDir, "./"));
                    System.err.println("    " + e.getMessage().replace(baseDir, "./").replace("\n", "\n    "));
                }
            }
        }
        if (errors > 0) {
            System.err.println("Done, Found " + errors + " errors");
            System.exit(1);
        }
    }


    private static void printUsage() {
        System.out.println("Usage: java -jar velovalidator.jar path_to_templates [path_to_config_file]");
    }
}
