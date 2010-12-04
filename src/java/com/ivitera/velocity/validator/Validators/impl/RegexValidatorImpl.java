package com.ivitera.velocity.validator.Validators.impl;

import com.ivitera.velocity.validator.Validators.Validator;
import com.ivitera.velocity.validator.utils.Lists;
import com.ivitera.velocity.validator.utils.StringUtils;
import com.ivitera.velocity.validator.utils.FileUtils;
import com.ivitera.velocity.validator.exceptions.ValidationException;
import com.ivitera.velocity.validator.exceptions.InitializationException;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public enum RegexValidatorImpl implements Validator {

    INSTANCE;

    private boolean enabled = false;

    private static List<Pattern> patterns = Lists.arrayList();
    private static final String LINES_DELIMITER = "\n";

    public void validate(String filename) throws Exception {
        validate(new File(filename));
    }

    public void validate(File file) throws Exception {
        for (Pattern pattern : patterns) {
            List<String> lines = FileUtils.readLines(file);
            String text = StringUtils.join(lines, LINES_DELIMITER);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String found = matcher.group(0);
                int startAt = text.indexOf(found);
                int line = text.substring(0, startAt).split(LINES_DELIMITER).length;
                String[] parts = found.split(LINES_DELIMITER);
                int column = lines.get(line - 1).indexOf(parts[0]) + 1;
                throw createException(file.getAbsolutePath(), pattern, found, line, column);
            }
        }
    }

    public void init(File config) throws InitializationException {
        try {
            List<String> lines = FileUtils.readLines(config);
            patterns.clear();
            enabled = false;
            for (String line : lines) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    patterns.add(Pattern.compile(trimmedLine));
                }
            }
            if (!patterns.isEmpty()) {
                enabled = true;
            }
        } catch (Exception e) {
            throw new InitializationException(e);
        }
    }

    private ValidationException createException(String file, Pattern pattern, String found,
                                                int line, int column) throws ValidationException {
        return new ValidationException(
                "Encountered \"" + pattern.toString() + "\" at " + file + "[line " + line
                        + ", column " + column + "]" + LINES_DELIMITER + "    " + found);
    }
}
