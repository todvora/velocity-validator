package com.ivitera.velocity.validator;


import java.io.File;

import com.ivitera.velocity.validator.exceptions.InputParamsException;

import org.junit.Assert;
import org.junit.Test;

public class ParamsParserTest {

    @Test
    public void testParsing() throws InputParamsException {
        String[] strings = {"-verbose","-rules=regex.conf", "data/templates"};
        ParamsParser paramsParser = new ParamsParser(strings);
        paramsParser.parse();

        assertPathsEqual("data/templates", paramsParser.getBaseDirFile());
        assertPathsEqual("regex.conf", paramsParser.getConfigFile());
        Assert.assertTrue(paramsParser.isVerbose());
    }

   @Test
    public void testParsingNoConfig() throws InputParamsException {
        String[] strings = {"-verbose","-rules=regex.conf", "data/templates"};
        ParamsParser paramsParser = new ParamsParser(strings);
        paramsParser.parse();

        assertPathsEqual("data/templates", paramsParser.getBaseDirFile());
        assertPathsEqual("regex.conf", paramsParser.getConfigFile());
        Assert.assertTrue(paramsParser.isVerbose());
    }

    @Test(expected = InputParamsException.class)
    public void incorrectParams() throws InputParamsException {
        new ParamsParser(new String[]{"-nonsense"}).parse();
    }
    
    private static void assertPathsEqual(String expected, File actual)
    {
    	File expectedFile = new File(expected);
    	Assert.assertEquals(expectedFile.getPath(), actual.getPath());
    }
}
