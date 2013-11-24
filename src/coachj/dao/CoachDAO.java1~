package coachj.dao;

import coachj.models.Coach;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Data Access Object for the Coach class
 *
 * @author Eduardo M. Rodrigues
 * @version 1.0
 * @date 26/07/2013
 */
public class CoachDAO {

    /**
     * Finds a coach by his id
     * 
     * @param id Id to be found
     * @return Coach object
     */
    public Coach findId(Integer id) {
        /**
         * Creates an entity manager instance and tries to find the 
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Coach coach = em.find(Coach.class, id);

        try {            
            em.getTransaction().commit();
        } catch (Exception e) {            
            System.out.println("Erro ao acessar dados:\n" + e.getMessage());
        } finally {            
            em.close();
        }
        return coach;
    }

    /**
     * Inserts a new coach into database
     * 
     * @param coach Coach object to be inserted
     */
    public void insert(Coach coach) {
        /**
         * Creates an entity manager instance and persists the new
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        em.persist(coach);

        try {            
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
    public void update(Coach coach) {
         /**
         * Creates an entity manager instance and merges the changes into the
         * record
         */
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        em.merge(coach);

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
        Coach toRemove = em.find(Coach.class, id);
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
    public Coach first() {
        EntityManager em = JPAUtil.getInstance().createEntityManager();
        Query query = em.createQuery("SELECT c FROM Coach c ORDER BY c.id ASC");
        query.setMaxResults(1);

        Coach coach = null;
        try {
            coach = (Coach) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Table is empty..."); // delete
        } finally {
            em.close();
        }
        return coach;
    }
}
