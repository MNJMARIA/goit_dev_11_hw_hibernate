import client.Client;
import client.ClientCrudService;
import client.IClientCrudService;
import database.DatabaseInitService;
import org.junit.jupiter.api.*;

import java.util.List;

public class ClientCrudServiceTests {
    private IClientCrudService clientCrudService;

    @BeforeEach
    public void beforeEach(){
        new DatabaseInitService().initDb();
        clientCrudService = new ClientCrudService();
    }

    @Test
    public void testCreateClientWorksOk(){
        String clientName = "Wei Wuxian";

        Client createdClient = clientCrudService.create(clientName);

        Assertions.assertNotNull(createdClient.getId());
        Assertions.assertEquals(clientName, createdClient.getName());

        // Clean up
        clientCrudService.delete(createdClient.getId());
    }

    @Test
    public void testGetClientById1WorksOk(){
        long existingClientId = 1;

        Client actualClient = clientCrudService.getById(existingClientId);

        //info about existing client
        Client expectedClient = new Client();
        expectedClient.setId(existingClientId);
        expectedClient.setName("Adel");

        Assertions.assertNotNull(actualClient);
        Assertions.assertEquals(existingClientId, actualClient.getId());
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testGetClientById5WorksOk(){
        long existingClientId = 5;

        Client actualClient = clientCrudService.getById(existingClientId);

        //info about existing client
        Client expectedClient = new Client();
        expectedClient.setId(existingClientId);
        expectedClient.setName("Mariia");

        Assertions.assertNotNull(actualClient);
        Assertions.assertEquals(existingClientId, actualClient.getId());
        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testGetAllClientsWorksOk(){
        List<Client> clients = clientCrudService.getAll();

        Assertions.assertNotNull(clients);
        Assertions.assertFalse(clients.isEmpty());
        // Check if there are exactly 10 clients (created by migration)
        Assertions.assertEquals(10, clients.size());
    }
    @Test
    public void testGetClientIdByNameWorksOk(){
        String existingClientName = "Bell";

        long actualClientId = clientCrudService.getIdByName(existingClientName);
        long expectedClientId = 2;

        Assertions.assertNotEquals(-1, actualClientId);
        Assertions.assertEquals(expectedClientId, actualClientId);
    }
    @Test
    public void testUpdateClientWorksOk(){
        String originalName = "Lan Wanji";
        String updatedName = "New Lan Wanji";

        Client createdClient = clientCrudService.create(originalName);

        Client updatedClient = clientCrudService.update(createdClient.getId(), updatedName);

        Assertions.assertNotNull(updatedClient);
        Assertions.assertEquals(updatedName, updatedClient.getName());

        clientCrudService.delete(updatedClient.getId());
    }

   @Test
    public void testDeleteClientWorksOk(){
       String clientName = "NEWCLIENTTODELETE";
       Client createdClient = clientCrudService.create(clientName);

       boolean isDeleted = clientCrudService.delete(createdClient.getId());

       Assertions.assertTrue(isDeleted);
       Assertions.assertNull(clientCrudService.getById(createdClient.getId()));
    }
}
