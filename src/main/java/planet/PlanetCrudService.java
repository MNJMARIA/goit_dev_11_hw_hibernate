package planet;

import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class PlanetCrudService implements IPlanetCrudService{

    @Override
    public Planet create(Planet planet) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Planet newPlanet = new Planet();
            newPlanet.setName(planet.getName());
            newPlanet.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            session.persist(newPlanet);
            transaction.commit();
            return newPlanet;
        }
    }

    @Override
    public Planet getById(String id) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.get(Planet.class, id);
        }
    }

    @Override
    public String getIdByName(String name) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Planet planet = session.createQuery("FROM Planet WHERE name = :name", Planet.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return planet != null ? planet.getId() : "";
        }
    }

    @Override
    public List<Planet> getAll() {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.createQuery("FROM Planet", Planet.class).list();
        }
    }

    @Override
    public Planet update(String id, String name) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Planet planetToUpdate = session.get(Planet.class, id);
            if (planetToUpdate != null) {
                planetToUpdate.setName(name);
                session.persist(planetToUpdate);
                transaction.commit();
            }
            return planetToUpdate;
        }
    }

    @Override
    public boolean delete(String id) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
                Planet planetToDelete = session.get(Planet.class, id);
                if(planetToDelete != null){
                    session.remove(planetToDelete);
                    transaction.commit();
                    return true;
                }
            return false;
        }
    }
}
