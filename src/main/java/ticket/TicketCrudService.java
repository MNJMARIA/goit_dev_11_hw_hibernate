package ticket;

import client.Client;
import client.ClientCrudService;
import client.IClientCrudService;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import planet.IPlanetCrudService;
import planet.Planet;
import planet.PlanetCrudService;

import java.util.List;

public class TicketCrudService implements ITicketCrudService{
    private IPlanetCrudService pcs = new PlanetCrudService();
    private IClientCrudService ccs = new ClientCrudService();

    @Override
    public Ticket create(Ticket ticket) {
        //беремо планети та клієнта з квитка
        Client client = ticket.getClient();
        Planet fromPlanet = ticket.getFromPlanet();
        Planet toPlanet = ticket.getToPlanet();

        //перевірка взятих плаент та клієнта
        if (client == null || client.getId() == 0 || fromPlanet == null || fromPlanet.getId() == null || toPlanet == null || toPlanet.getId() == null) {
            return null; // Перевіряємо, щоб клієнт та обидві планети були не null і мали валідні ідентифікатори
        }

        //знаходимо клієнта та планети в БД
        client = ccs.getById(client.getId());
        fromPlanet = pcs.getById(fromPlanet.getId());
        toPlanet = pcs.getById(toPlanet.getId());

        if (client == null || fromPlanet == null || toPlanet == null) {
            return null; // Перевіряємо, чи існують клієнт та обидві планети з такими ідентифікаторами
        }

        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            return ticket;
        } catch (Exception e) {
            // якщо кинувся exception тоді повертаємо пустий квиток
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket getById(long id) {
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.get(Ticket.class, id);
        }
    }

    @Override
    public List<Ticket> getAll() {
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            return session.createQuery("FROM Ticket", Ticket.class).list();
        }
    }

    @Override
    public Ticket update(long id, Ticket ticket) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Ticket ticketToUpdate = session.get(Ticket.class, id);
            if (ticketToUpdate != null) {
                ticketToUpdate.setCreatedAt(ticket.getCreatedAt());
                ticketToUpdate.setClient(ticket.getClient());
                ticketToUpdate.setFromPlanet(ticket.getFromPlanet());
                ticketToUpdate.setToPlanet(ticket.getToPlanet());
                session.persist(ticketToUpdate);
                transaction.commit();
            }
            return ticketToUpdate;
        }
    }
    @Override
    public boolean delete(long id) {
        try(Session session= HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            Ticket ticketToDelete = session.get(Ticket.class, id);
            if(ticketToDelete != null){
                session.remove(ticketToDelete);
                transaction.commit();
                return true;
            }
            return false;
        }
    }
}