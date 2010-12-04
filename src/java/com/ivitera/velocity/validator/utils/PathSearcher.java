package com.ivitera.velocity.validator.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PathSearcher {
    public static List<File> getFileListing(File aStartingDir, FileFilter filter)
            throws FileNotFoundException {
        validateDirectory(aStartingDir);
        List<File> result = getFileListingNoSort(aStartingDir, filter);
        Collections.sort(result);
        return result;
    }

    private static List<File> getFileListingNoSort(File aStartingDir, FileFilter filter)
            throws FileNotFoundException {
        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        if (filesAndDirs != null) {
            List<File> filesDirs = Arrays.asList(filesAndDirs);
            for (File file : filesDirs) {
                if (filter.accept(file)) {
                    result.add(file); //always add, even if directory
                }
                if (!file.isFile()) {
                    //must be a directory
                    //recursive call!
                    List<File> deeperList = getFileListingNoSort(file, filter);
                    result.addAll(deeperList);
                }
            }
        }
        return result;
    }

    /**
     * Directory is valid if it exists, does not represent a file, and can be read.
     */
    private static void validateDirectory(File aDirectory) throws FileNotFoundException {
        if (aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }
        if (!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }
        if (!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: " + aDirectory);
        }
        if (!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
        }
    }
}
