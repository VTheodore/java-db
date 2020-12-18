import entities.Label;
import entities.ingredients.Ingredient;
import entities.ingredients.impl.AmmoniumChloride;
import entities.ingredients.impl.Mint;
import entities.ingredients.impl.Nettle;
import entities.shampoos.Shampoo;
import entities.shampoos.impl.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("shampoo_company");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Ingredient am = new AmmoniumChloride();
        Ingredient mint = new Mint();
        Ingredient nettle = new Nettle();

        Label label = new Label("Fresh Nuke Shampoo", "Contains mint and nettle");
        Shampoo shampoo = new FreshNuke(label);

        shampoo.getIngredients().add(mint);
        shampoo.getIngredients().add(nettle);
        shampoo.getIngredients().add(am);

        entityManager.persist(shampoo);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
