package ticket;

import client.Client;

import java.util.List;

public interface ITicketCrudService {
    Ticket create(Ticket ticket);
    Ticket getById(long id);
    Ticket update(long id, Ticket ticket);
    List<Ticket> getAll();
    boolean delete(long id);
}
