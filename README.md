![Build Status](https://travis-ci.org/j-diaz/any-backend.svg?branch=master)

Any Backend
============

A a database abstraction library. Primarily, its a wrapper over JDBC. It uses HikaryCP for JDBC connection pooling.

###Example Usage

```java
import com.scriptfuzz.backend.BackendResult;
import com.scriptfuzz.backend.BackendResultSet;
import com.scriptfuzz.backend.PostgresBackend;
import com.scriptfuzz.backend.Backend;
 
public class Example {    
    
    // Our JDBC pooled postgres connection
    private Backend postgres;
    
    public Example(){        
        // No path needed if file is found in src/main/resources
        String pathToProperties = "database.properties"; 
        postgres = new PostgresBackend(pathToProperties);
    }
    
    public String setupTable() {
       
       // create a table
       postgres.createOrModify("CREATE TABLE Person("
                                + "id INTEGER NOT NULL IDENTITY PRIMARY KEY, "
                                + "firstname VARCHAR(100), "
                                + "lastname VARCHAR(100) "
                                + ")", null
                                );
                                
        // Add some data
        postgres.insert("INSERT INTO Person values (1, 'Bob', 'Smith')");                        
    }
    
    public String fetchLastName() {
        
        Map<String, Object> values = new HashMap<>();
        values.put("id", 1);        
        values.put("name", "Bob");
        // Backend result sets are very simillar to JDBC ResultSets.
        // Data found in the values HashMap gets replaced into the query before executing 
        // the query.
        BackendResultSet brs = postgres.read("SELECT * FROM Person WHERE id = [id] and firstname = '[name]'", values);
        
        String lastname = "";
        // Iterate through results
        for ( BackendResult row : brs.getRows() ) {
            // Get column value
            lastname = row.getString("LASTNAME");
            break;
        }
        
        return lastname;
    }
   
}

```


As per the previous example, you don't have to worry about forgetting to close an SQL connection. AnyBackend handles JDBC nuances for us and abstracts away the pain 
points commonly dealt with working with the JDBC API

###Contributing

All contributors are welcome. Create a pull request or contact me [@__diaz](https://www.twitter.com/__diaz)
