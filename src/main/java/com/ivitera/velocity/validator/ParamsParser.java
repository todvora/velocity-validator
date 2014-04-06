package com.ivitera.velocity.validator;

import org.apache.log4j.Logger;

import java.io.File;


class ParamsParser {

    private static final Logger log = Logger.getLogger(ParamsParser.class);

    private String[] args;
    private boolean verbose;
    private File configFile;
    private File baseDirFile;

    public ParamsParser(String... args) {
        this.args = args;
    }

    private static void printUsage() {
        log.info("Usage: java -jar velovalidator.jar path_to_templates [-rules=path_to_config_file] [-verbose]");
    }

    public boolean isVerbose() {
        return verbose;
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getBaseDirFile() {
        return baseDirFile;
    }

    public ParamsParser invoke() {
        if (args.length < 1 || args.length > 3) {
            printUsage();
            System.exit(1);
        }

        String baseDirPath = null;
        verbose = false;
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
                baseDirPath = arg;
            }
        }


        if(baseDirPath == null) {
            baseDirPath = System.getProperty("user.dir");
        }

        if(!baseDirPath.endsWith("/")) {
            baseDirPath = baseDirPath + "/";
        }

        configFile = null;
        if(additionalRulesFile != null) {
            configFile = new File(additionalRulesFile);
        }

        baseDirFile = new File(baseDirPath);
        return this;
    }
}
