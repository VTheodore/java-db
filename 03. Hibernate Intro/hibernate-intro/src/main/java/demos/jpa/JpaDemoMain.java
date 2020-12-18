package demos.jpa;

import demos.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaDemoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();

        User user = new User("Pesho");

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction() != null) {
                em.getTransaction().rollback();
            }

            throw e;
        }

        User u = em.find(User.class, 1); // no need for transaction

        em.getTransaction().begin();
        em.detach(u);
        u.setUsername("Testtttt");
        em.merge(u);
        em.getTransaction().commit();

        System.out.println(u);

        em.getTransaction().begin();
        em.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(1)
                .setMaxResults(5)
                .getResultList()
                .forEach(System.out::println);
        em.getTransaction().commit();

        em.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(criteriaBuilder.like(root.get("username"), "T%"));
        em.createQuery(query).setMaxResults(3).getResultList().forEach(System.out::println);
        em.getTransaction().commit();
        em.close();
    }
}
