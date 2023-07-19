/*
import client.Client;
import database.DatabaseInitService;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import planet.Planet;

public class App {
    public static void main(String[] args) {
        DatabaseInitService databaseService = new DatabaseInitService();

        databaseService.initDb();

        //CREATE CLIENT
        Client newClient = new Client();
        newClient.setName("NEWCLIENT");

        //Persist object
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
            Transaction transaction1 = session.beginTransaction();
                session.persist(newClient);
                Client createdClient = session.get(Client.class, newClient.getId());
            transaction1.commit();
        session.close();

        System.out.println("RESULT1!!!!!!!!!!!!!!!! " + createdClient); //name=NEWCLIENT, id=?


        //DELETE CLIENT
        //Remove object
        session = HibernateUtil.getInstance().getSessionFactory().openSession();
            Transaction transaction2 = session.beginTransaction();
                Client deletedClient = session.get(Client.class, newClient.getId());
                session.remove(deletedClient);
            transaction2.commit();
        session.close();

        System.out.println("RESULT2!!!!!!!!!!!!!!!! " + deletedClient); //name=null, id=null


        //CREATE PLANET
        Planet newPlanet = new Planet();
        newPlanet.setId("ABC123");
        newPlanet.setName("ABRACADABRA");

        //Persist object
        session = HibernateUtil.getInstance().getSessionFactory().openSession();
            Transaction transaction3 = session.beginTransaction();
                session.persist(newPlanet);
                Planet createdPlanet = session.get(Planet.class, "ABC123");
            transaction3.commit();
        session.close();

        System.out.println("RESULT3!!!!!!!!!!!!!!!! " +createdPlanet); //name=ABRACADABRA, id=ABC123


        //DELETE PLANET
        //Remove object
        session = HibernateUtil.getInstance().getSessionFactory().openSession();
            Transaction transaction4 = session.beginTransaction();
                session.delete(newPlanet);
                Planet deletedPlanet = session.get(Planet.class, "ABC123");
            transaction4.commit();
        session.close();

        System.out.println("RESULT4!!!!!!!!!!!!!!!! " + deletedPlanet); //name=null, id=null

    }
}
*/
