package com.scriptfuzz.backend;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jdiaz on 9/21/16.
 */
public class H2BackendTest {

    private Backend h2;

    @Before
    public void setup() throws IOException {
        h2 = new H2Backend("h2database.properties");
    }

    @Test
    public void createTable(){
        final String query = "CREATE TABLE ("
                            + ")";
      //  h2.();
        // TODO: implement H2 Db test
    }
}
