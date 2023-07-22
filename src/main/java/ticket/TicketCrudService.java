package ticket;

import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class TicketCrudService implements ITicketCrudService{

    @Override
    public Ticket create(Ticket ticket) {
        try(Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            return ticket;
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