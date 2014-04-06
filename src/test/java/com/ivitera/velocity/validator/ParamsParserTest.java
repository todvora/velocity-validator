package com.ivitera.velocity.validator;


import com.ivitera.velocity.validator.exceptions.InputParamsException;
import org.junit.Assert;
import org.junit.Test;

public class ParamsParserTest {

    @Test
    public void testParsing() throws InputParamsException {
        String[] strings = {"-verbose","-rules=regex.conf", "data/templates"};
        ParamsParser paramsParser = new ParamsParser(strings);
        paramsParser.parse();

        Assert.assertEquals("data/templates", paramsParser.getBaseDirFile().getPath());
        Assert.assertEquals("regex.conf", paramsParser.getConfigFile().getPath());
        Assert.assertTrue(paramsParser.isVerbose());
    }

   @Test
    public void testParsingNoConfig() throws InputParamsException {
        String[] strings = {"-verbose","-rules=regex.conf", "data/templates"};
        ParamsParser paramsParser = new ParamsParser(strings);
        paramsParser.parse();

        Assert.assertEquals("data/templates", paramsParser.getBaseDirFile().getPath());
        Assert.assertEquals("regex.conf", paramsParser.getConfigFile().getPath());
        Assert.assertTrue(paramsParser.isVerbose());
    }

    @Test(expected = InputParamsException.class)
    public void incorrectParams() throws InputParamsException {
        new ParamsParser(new String[]{"-nonsense"}).parse();
    }
}
