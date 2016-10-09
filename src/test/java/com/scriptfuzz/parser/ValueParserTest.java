package com.scriptfuzz.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdiaz on 10/8/16.
 */
public class ValueParserTest {

    @Test
    public void replaceValuesTest() {
        String query = "SELECT * FROM Person WHERE id = [id] and firstname = '[name]'";
        Map<String,Object> values = new HashMap<>();
        values.put("id", 1);
        values.put("name", "Bob");

        final String expected = "SELECT * FROM Person WHERE id = 1 and firstname = 'Bob'";
        System.out.println(ValueParser.replace(query, values));
        Assert.assertTrue("Query must replace values correctly", expected.equals(ValueParser.replace(query, values)));
    }

    @Test
    public void emptyValuesMapTest() {

        final String query = "SELECT * FROM Person WHERE id = 1 and firstname = 'Bob'";
        Map<String,Object> values = new HashMap<>();

        final String expected = "SELECT * FROM Person WHERE id = 1 and firstname = 'Bob'";
        System.out.println(ValueParser.replace(query, values));
        Assert.assertTrue("Query must replace values correctly", expected.equals(ValueParser.replace(query, values)));
    }
}
