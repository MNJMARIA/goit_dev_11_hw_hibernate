import client.ClientCrudService;
import client.IClientCrudService;
import database.DatabaseInitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import planet.IPlanetCrudService;
import planet.PlanetCrudService;
import ticket.ITicketCrudService;
import ticket.Ticket;
import ticket.TicketCrudService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TicketCrudServiceTests {
    private static ITicketCrudService ticketCrudService;
    private IClientCrudService ccs = new ClientCrudService();
    private IPlanetCrudService pcs = new PlanetCrudService();

    @BeforeEach
    public void beforeEach(){
        new DatabaseInitService().initDb();
        ticketCrudService = new TicketCrudService();
    }

    @Test
    public void testCreateTicketWorksOk(){
        Ticket newTicket = new Ticket();
        long id = ticketCrudService.getAll().size() + 1;
        newTicket.setId(id);
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(pcs.getById("EARTH"));
        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNotNull(createdTicket);

        // Clean up
        ticketCrudService.delete(createdTicket.getId());
    }

    @Test
    public void testGetTicketEarthMarsByIdWorksOk(){
        long existingTicketId = 2;

        Ticket actualTicket = ticketCrudService.getById(existingTicketId);

        //info about existing ticket
        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(existingTicketId);
        expectedTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        expectedTicket.setClient(ccs.getById(1));
        expectedTicket.setFromPlanet(pcs.getById("EARTH"));
        expectedTicket.setToPlanet(pcs.getById("MARS"));

        Assertions.assertNotNull(actualTicket);
        Assertions.assertEquals(existingTicketId, actualTicket.getId());
        Assertions.assertEquals(expectedTicket, actualTicket);
    }

    @Test
    public void testGetTicketMarsUranusByIdWorksOk(){
        long existingTicketId = 6;

        Ticket actualTicket = ticketCrudService.getById(existingTicketId);

        //info about existing ticket
        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(existingTicketId);
        expectedTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        expectedTicket.setClient(ccs.getById(6));
        expectedTicket.setFromPlanet(pcs.getById("MARS"));
        expectedTicket.setToPlanet(pcs.getById("URANUS"));

        Assertions.assertNotNull(actualTicket);
        Assertions.assertEquals(expectedTicket, actualTicket);
    }

    @Test
    public void testGetAllTicketsWorksOk(){
        List<Ticket> tickets = ticketCrudService.getAll();

        Assertions.assertNotNull(tickets);
        Assertions.assertFalse(tickets.isEmpty());
        // Check if there are exactly 10 tickets (created by migration)
        Assertions.assertEquals(8, tickets.size());
    }
    @Test
    public void testUpdatePlanetWorksOk(){

        Ticket originalTicket = new Ticket();
        originalTicket.setId(ticketCrudService.getAll().size() + 1);
        originalTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        originalTicket.setClient(ccs.getById(5));
        originalTicket.setFromPlanet(pcs.getById("EARTH"));
        originalTicket.setToPlanet(pcs.getById("MARS"));

        Ticket ticketToUpdate = new Ticket();
        ticketToUpdate.setId(ticketCrudService.getAll().size() + 1);
        ticketToUpdate.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        ticketToUpdate.setClient(ccs.getById(5));
        ticketToUpdate.setFromPlanet(pcs.getById("EARTH"));
        ticketToUpdate.setToPlanet(pcs.getById("NEPTUNE"));

        Ticket createdTicket = ticketCrudService.create(originalTicket);

        Ticket updatedTicket = ticketCrudService.update(originalTicket.getId(), ticketToUpdate);

        Assertions.assertNotNull(updatedTicket);
        Assertions.assertEquals(createdTicket.getId(), updatedTicket.getId());

        ticketCrudService.delete(updatedTicket.getId());
        ticketCrudService.delete(createdTicket.getId());
    }

    @Test
    public void testDeleteWorksOk(){
        Ticket newTicket = new Ticket();
        long id = ticketCrudService.getAll().size() + 1;
        newTicket.setId(id);
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(3));
        newTicket.setFromPlanet(pcs.getById("NEPTUNE"));
        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        boolean isDeleted = ticketCrudService.delete(createdTicket.getId());

        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(ticketCrudService.getById(createdTicket.getId()));
    }
}
