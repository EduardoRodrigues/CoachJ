package coachj.callables;

import coachj.utils.MySqlUtils;
import java.util.concurrent.Callable;

/**
 * Creates a thread to check the database's tables
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/07/2013
 */
public class DatabaseTablesVerifier implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {         
        return MySqlUtils.checkDatabaseTables();
    }
} // end DatabaseTablesVerifier
