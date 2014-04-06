package com.ivitera.velocity.validator.utils;


import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class StringUtilsTest {

    @Test
    public void testJoin() {
        List<String> data = Arrays.asList("lorem", "ipsum", "dolor");
        Assert.assertEquals("lorem ipsum dolor", StringUtils.join(data, " "));

        Set<String> set = new LinkedHashSet<>();
        set.add("foo");
        set.add("bar");

        Assert.assertEquals("foo\nbar", StringUtils.join(set, "\n"));

    }
}
