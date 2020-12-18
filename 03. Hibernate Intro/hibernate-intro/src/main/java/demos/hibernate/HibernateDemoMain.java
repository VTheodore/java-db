package demos.hibernate;

import demos.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HibernateDemoMain {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Student student = new Student("Ivan Petrov");

            try {
                session.beginTransaction();
                session.save(student);
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }

                throw e;
            }

            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);
            Student s1 = session.get(Student.class, 1);
            session.getTransaction().commit();

            System.out.println(s1);

            session.beginTransaction();
            session.createQuery("FROM Student WHERE id > 2", Student.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .stream()
                    .forEach(System.out::println);
            session.getTransaction().commit();

            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
            Root<Student> root = criteria.from(Student.class);

            criteria.select(root).where(builder.like(root.get("name"), "I%"));
            List<Student> students = session.createQuery(criteria).getResultList();

            for (Student st : students) {
                System.out.println(st);
            }

            session.getTransaction().commit();
        }
    }
}
