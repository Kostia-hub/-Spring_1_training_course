import domain.Client;
import domain.Product;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory entityFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = entityFactory.createEntityManager();

        createEntity(em);
    }

    private static void createEntity(EntityManager em){

        System.out.println("Creating entity");
        //declare entity
        Client client1 = new Client();
        client1.setName("Vasiliy");
        client1.setEmail("abs@mail.ru");
        client1.setPhoneNumber("880023231");

        Client client2 = new Client();
        client2.setName("Ivan");
        client2.setEmail("dfg@mail.ru");
        client2.setPhoneNumber("990023231");

        Product pr1 = new Product();
        pr1.setTitle("Bread");
        pr1.setPrice("45");

        Product pr2 = new Product();
        pr2.setTitle("Milk");
        pr2.setPrice("55");

        Product pr3 = new Product();
        pr3.setTitle("Beer");
        pr3.setPrice("75");
        //open transaction
        em.getTransaction().begin();
        //put person into persist area of Hibernate
        em.persist(client1);
        em.persist(client2);
        em.persist(pr1);
        em.persist(pr2);
        em.persist(pr3);
        //commit/close transaction
        em.getTransaction().commit(); //Пока не закомитили транзакцию, ничего в БД не сохранилось!!!
        //em.getTransaction().rollback(); //Если роллбэк, то ничего в БД не сохранится

        System.out.println("Creating finished");

    }

//    private static Person readEntity(EntityManager em, long id){
//        System.out.println("Start reading");
//
//        em.getTransaction().begin();
//        Person person = em.find(Person.class, id);
//        em.getTransaction().commit();
//
//        System.out.println("Reading completed->" + person);
//        return person;
//    }
//
//    private static void saveEntity(EntityManager em, Person entity){
//        System.out.println("Start saving");
//
//        em.getTransaction().begin();
//        Person savedEntity = em.merge(entity);
//        em.getTransaction().commit();
//
//        System.out.println("Saving completed->" + savedEntity);
//    }


}