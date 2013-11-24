package coachj.dao;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility methods for the Java Persistence API
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 22/07/2013
 */
public class JPAUtil {

    /**
     * JPA instance
     */
    private static JPAUtil instance;
    
    /**
     * Entity manager instance
     */
    private EntityManagerFactory factory;

    /**
     * Constructor
     */
    private JPAUtil() {
        Map properties = new HashMap();        
        factory = Persistence.createEntityManagerFactory("CoachJPU", properties);
    }

    /**
     * Returns the current JPA instance
     * @return 
     */
    public static JPAUtil getInstance() {
        if (instance == null) {
            instance = new JPAUtil();
        }
        return instance;
    }

    /**
     * Creates and returns an entity manager object
     * @return 
     */
    public EntityManager createEntityManager(){
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager;
    }
}
