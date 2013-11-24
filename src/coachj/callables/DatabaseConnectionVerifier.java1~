package coachj.callables;

import coachj.utils.MySqlUtils;
import java.util.concurrent.Callable;

/**
 * Creates a thread to check the database's connection
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/07/2013
 */
public class DatabaseConnectionVerifier implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {        
        return MySqlUtils.checkDatabaseConnection();
    }
} // end DatabaseConnectionVerifier
