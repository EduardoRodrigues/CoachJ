
package coachj.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Data Access Object class for all classes
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 25/07/2013
 */
public class ObjectDAO {

    Object objectClass;
    String referencedTable;
    String idField;

    public ObjectDAO(Object objectClass, String referencedTable, String idField) {
        this.objectClass = objectClass.getClass();
        this.referencedTable = referencedTable;
        this.idField = idField;
    }
           
    /**
     * Finds a coach by his id
     * 
     * @param id Id to be found
     * @return Coach object
     */
    public Object findId(Integer id) {
        /**
         * Creates an entity manager instance and tries to find the 
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Object object = em.find(objectClass.getClass(), id);
        
        try {            
            em.getTransaction().commit();
        } catch (Exception e) {            
            System.out.println("Erro ao acessar dados:\n" + e.getMessage());
        } finally {            
            em.close();
        }
        return object;
    }

    /**
     * Inserts a new coach into database
     * 
     * @param coach Coach object to be inserted
     */
    public void insert(Object objectClass) {
        /**
         * Creates an entity manager instance and persists the new
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        System.out.println("persisting"); // delete
        em.persist(objectClass);

        try {   
            em.flush();
            em.clear();
            System.out.println("committing"); // delete
            em.getTransaction().commit();
        } catch (Exception e) {           
            System.out.println("Erro ao inserir dados:\n" + e.getMessage());
        } finally {            
            em.close();
        }
    }

    /**
     * Updates an existing record
     * 
     * @param coach Coach object to be updated
     */
    public void update(Object objectClass) {
         /**
         * Creates an entity manager instance and merges the changes into the
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        em.merge(objectClass);

        try {
            // tentando gravar dados
            em.getTransaction().commit();
        } catch (Exception e) {
            // tratando poss�veis erros
            System.out.println("Erro ao alterar dados:\n" + e.getMessage());
        } finally {
            // fechando conex�o
            em.close();
        }
    }

    /**
     * Deletes a record from the database
     * 
     * @param id Id to be deleted
     */
    public void delete(Integer id) {
         /**
         * Creates an entity manager instance, finds the record and deletes it
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Object toRemove = em.find(objectClass.getClass(), id);
        em.remove(toRemove);

        try {           
            em.getTransaction().commit();
        } catch (Exception e) {            
            System.out.println("Error:\n" + e.getMessage());
        } finally {            
            em.close();
        }
    }

    /**
     * Moves to the first record in the table
     * 
     * @return Coach object
     */
    public Object first() {
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Query query = em.createQuery("SELECT t FROM " + referencedTable 
                + "t ORDER BY t." + idField + " ASC");
        query.setMaxResults(1);

        Object object = null;
        try {
            object = objectClass.getClass().cast(query.getSingleResult());
        } catch (Exception e) {
            System.out.println("Table '" + referencedTable + "' is empty...");
        } finally {
            em.close();
        }
        return object;
    }
    
} // end class ObjectDAO
