package client;

import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ClientCrudService implements IClientCrudService{
    @Override
    public Client create(String name){
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
                Client newClient = new Client();
                newClient.setName(name);
                session.persist(newClient);
            transaction.commit();
            return newClient;
        }
    }
    @Override
    public long getIdByName(String name){
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            Client client = session.createQuery("FROM Client WHERE name = :name", Client.class)
                    .setParameter("name", name)
                    .uniqueResult();
            return client != null ? client.getId() : -1;
        }
    }

    @Override
    public Client getById(long id) {
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.get(Client.class, id);
        }
    }

    @Override
    public List<Client> getAll() {
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.createQuery("FROM Client", Client.class).list();
        }
    }
    @Override
    public Client update(long id, String name){
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction= session.beginTransaction();
            Client clientToUpdate = session.get(Client.class, id);
            if(clientToUpdate != null){
                clientToUpdate.setName(name);
                session.persist(clientToUpdate);
                transaction.commit();
                return clientToUpdate;
            }
            return null;
        }
    }

    @Override
    public boolean delete(long id){
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Client clientToDelete = session.get(Client.class,id);
            if(clientToDelete != null){
                session.remove(clientToDelete);
                transaction.commit();
                return true;
            }
            return false;
        }
    }
}
