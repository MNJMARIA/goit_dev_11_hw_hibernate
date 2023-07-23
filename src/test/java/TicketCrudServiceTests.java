import client.ClientCrudService;
import client.IClientCrudService;
import database.DatabaseInitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import planet.IPlanetCrudService;
import planet.Planet;
import planet.PlanetCrudService;
import ticket.ITicketCrudService;
import ticket.Ticket;
import ticket.TicketCrudService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class TicketCrudServiceTests {
    private static ITicketCrudService ticketCrudService;
    private static IClientCrudService ccs;
    private static IPlanetCrudService pcs;

    @BeforeAll
    public static void setup(){
        new DatabaseInitService().initDb();
        ticketCrudService = new TicketCrudService();
        ccs = new ClientCrudService();
        pcs = new PlanetCrudService();
    }

    @Test
    public void testCreateTicketWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(pcs.getById("EARTH"));
        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNotNull(createdTicket);
        Assertions.assertNotEquals(0, createdTicket.getId()); // Check that the ID was generated and set properly
        // Clean up
        ticketCrudService.delete(createdTicket.getId());
    }
    @Test
    public void testCreateNotExistClientWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));

        // Знаходимо планету з бази даних за ідентифікатором "NOTEXIST" або "null"
        Planet fromPlanet = pcs.getById("NOTEXIST");
        newTicket.setFromPlanet(fromPlanet);

        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket); // Очікуємо, що квиток не був створений через неправильне значення планети "fromPlanet"
    }
    @Test
    public void testCreateNullClientWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(null); // Передаємо значення клієнта як null
        newTicket.setFromPlanet(pcs.getById("EARTH"));
        newTicket.setToPlanet(pcs.getById("MARS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket); // Очікуємо, що квиток не був створений через неправильне значення клієнта
    }
    @Test
    public void testCreateNotExistFromPlanetWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(pcs.getById("NOTEXIST")); //not exist from planet
        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket);
    }
    @Test
    public void testCreateNullFromPlanetWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(null); //null from planet
        newTicket.setToPlanet(pcs.getById("VENUS"));

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket);
    }
    @Test
    public void testCreateNotExistToPlanetWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(pcs.getById("EARTH"));
        newTicket.setToPlanet(pcs.getById("NOTEXIST")); //not exist to planet

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket);
    }
    @Test
    public void testCreateNullToPlanetWorksOk(){
        Ticket newTicket = new Ticket();
        newTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTicket.setClient(ccs.getById(1));
        newTicket.setFromPlanet(pcs.getById("EARTH"));
        newTicket.setToPlanet(null); //null to planet

        Ticket createdTicket = ticketCrudService.create(newTicket);

        Assertions.assertNull(createdTicket);
    }
    @Test
    public void testGetTicketEarthMarsByIdWorksOk(){
        long existingTicketId = 1;

        Ticket actualTicket = ticketCrudService.getById(existingTicketId);

        //info about existing ticket
        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(existingTicketId);
        expectedTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        expectedTicket.setClient(ccs.getById(1));
        expectedTicket.setFromPlanet(pcs.getById("EARTH"));
        expectedTicket.setToPlanet(pcs.getById("MARS"));

        Assertions.assertNotNull(actualTicket);
        Assertions.assertEquals(expectedTicket.getId(), actualTicket.getId());
        //Assertions.assertEquals(expectedTicket.getCreatedAt(), actualTicket.getCreatedAt());
        Assertions.assertEquals(expectedTicket.getClient().getId(), actualTicket.getClient().getId());
        Assertions.assertEquals(expectedTicket.getFromPlanet().getId(), actualTicket.getFromPlanet().getId());
        Assertions.assertEquals(expectedTicket.getToPlanet().getId(), actualTicket.getToPlanet().getId());
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
        Assertions.assertEquals(expectedTicket.getId(), actualTicket.getId());
        //Assertions.assertEquals(expectedTicket.getCreatedAt(), actualTicket.getCreatedAt());
        Assertions.assertEquals(expectedTicket.getClient().getId(), actualTicket.getClient().getId());
        Assertions.assertEquals(expectedTicket.getFromPlanet().getId(), actualTicket.getFromPlanet().getId());
        Assertions.assertEquals(expectedTicket.getToPlanet().getId(), actualTicket.getToPlanet().getId());

    }
    @Test
    public void testGetAllTicketsWorksOk(){
        List<Ticket> tickets = ticketCrudService.getAll();

        Assertions.assertNotNull(tickets);
        Assertions.assertFalse(tickets.isEmpty());
        // Check if there are exactly 10 tickets (created by migration)
        Assertions.assertEquals(10, tickets.size());
    }
    @Test
    public void testUpdatePlanetWorksOk(){

        Ticket originalTicket = new Ticket();
        originalTicket.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        originalTicket.setClient(ccs.getById(5));
        originalTicket.setFromPlanet(pcs.getById("EARTH"));
        originalTicket.setToPlanet(pcs.getById("MARS"));

        Ticket createdTicket = ticketCrudService.create(originalTicket);

        Ticket ticketToUpdate = new Ticket();
        ticketToUpdate.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        ticketToUpdate.setClient(ccs.getById(5));
        ticketToUpdate.setFromPlanet(pcs.getById("EARTH"));
        ticketToUpdate.setToPlanet(pcs.getById("NEPTUNE"));

        Ticket updatedTicket = ticketCrudService.update(originalTicket.getId(), ticketToUpdate);

        Assertions.assertNotNull(updatedTicket);
        Assertions.assertEquals(createdTicket.getId(), updatedTicket.getId());

        ticketCrudService.delete(updatedTicket.getId());
        //ticketCrudService.delete(createdTicket.getId());
    }
    @Test
    public void testDeleteWorksOk(){
        Ticket newTicket = new Ticket();
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