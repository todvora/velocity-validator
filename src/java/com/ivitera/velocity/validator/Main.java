package com.ivitera.velocity.validator;

import com.ivitera.velocity.validator.Validators.Validator;
import com.ivitera.velocity.validator.Validators.ValidatorsService;
import com.ivitera.velocity.validator.utils.PathSearcher;
import com.ivitera.velocity.validator.exceptions.InitializationException;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
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
            ValidatorsService.init(new File(args[1]));
        } catch (Exception e) {
            throw new InitializationException(e);
        }


        List<Validator> validators = ValidatorsService.getAllValidators();
        int errors = 0;
        for (Validator validator : validators) {
            for (File f : files) {
                try {
                    validator.validate(f);
                } catch (Exception e) {
                    errors++;
                    System.out.println("Error in file " + f.getAbsolutePath().replace(baseDir, "./"));
                    System.out.println("    " + e.getMessage().replace(baseDir, "./").replace("\n", "\n    "));
                }
            }
        }
        if (errors > 0) {
            System.out.println("Done, Found " + errors + " errors");
            System.exit(1);
        }
    }


    private static void printUsage() {
        System.out.println("Usage: java -jar velovalidator.jar path_to_templates path_to_config_file");
    }
}
