package com.scriptfuzz.backend;

import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by jdiaz on 9/21/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class H2BackendTest {

    private static Backend h2;

    @BeforeClass
    public static void setup() throws IOException {

        System.setProperty("dataSourceUrl", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource");
        h2 = new H2Backend();


        final String drop = "DROP TABLE IF EXISTS Person";
        h2.createOrModify(drop, null);

        final String query = "CREATE TABLE Person("
                +"id INTEGER NOT NULL IDENTITY PRIMARY KEY, "
                + "firstname VARCHAR(100), "
                + "lastname VARCHAR(100) "
                + ")";

        // Create test table
        h2.createOrModify(query, new HashMap<String, Object>());

        // Populate table

        final String insert = "INSERT INTO Person values (1, 'Bob', 'Smith')";
        h2.insert(insert, null);
    }

    @Test
    public void findUsers(){
        BackendResultSet brs = h2.read("SELECT * FROM Person", null);

        Assert.assertTrue("Database should contain at least 1 record", brs.getCount() > 0);
    }

    @Test
    public void insertUser(){
      BackendResultSet brs = h2.insert("INSERT INTO Person values (2, 'Ted', 'Mosby')", null);

        long id = brs.getInsertedId();

        Assert.assertTrue("Insert returns an id", id > 0);

        brs = h2.read("SELECT * FROM Person", null);

        Assert.assertTrue("Insert increased the number of records in db", brs.getCount() > 1);

    }

    @Test
    public void updateUser(){
        h2.update("UPDATE Person SET lastname = 'Marley' WHERE id = '1'", null);

        BackendResultSet brs = h2.read("SELECT * FROM Person WHERE id = '1'", null);

        String lastname = "";
        for ( BackendResult row : brs.getRows() ) {
            lastname = row.getString("LASTNAME");
            break;
        }

        Assert.assertTrue("Lastname should be Marley after update", "Marley".equals(lastname));

    }

    @Test
    public void zremoveUser(){
        h2.delete("DELETE FROM Person", null);

        BackendResultSet brs = h2.read("SELECT * FROM Person", null);

        Assert.assertTrue("Person table should be empty.", brs.getCount() == 0);
    }





}
