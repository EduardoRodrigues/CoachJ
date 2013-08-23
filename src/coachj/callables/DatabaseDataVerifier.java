package coachj.callables;

import coachj.utils.MySqlUtils;
import java.util.concurrent.Callable;

/**
 * Creates a thread to check the database's data integrity
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 23/07/2013
 */
public class DatabaseDataVerifier implements Callable<Boolean>{

    @Override
    public Boolean call() throws Exception {         
        return MySqlUtils.checkDatabaseIntegrity();
    }
} // end class DatabaseDataVerifier
