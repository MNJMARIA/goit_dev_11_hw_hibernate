import client.Client;
import client.ClientCrudService;
import database.Database;
import database.DatabaseInitService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClientCrudServiceTests {
    private static Connection connection;
    private static ClientCrudService clientCrudService;
    private long idOfCreatedClientWeiWuxian = clientCrudService.getIdByName("Wei Wuxian");

    @BeforeAll
    public static void initDb(){
        DatabaseInitService databaseInitService = new DatabaseInitService();
        databaseInitService.initDb();
        connection = Database.getInstance().getConnection();
        clientCrudService = new ClientCrudService(connection);

    }
   /* @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }*/

    @Test
    public void testThatMethodCreateWeiWuxianWorksOk(){
        clientCrudService.create("Wei Wuxian");
        idOfCreatedClientWeiWuxian = clientCrudService.getIdByName("Wei Wuxian");

        Assertions.assertNotNull(idOfCreatedClientWeiWuxian);
    }

    @Test
    public void testThatMethodGetById1WorksOk(){
        String actual = clientCrudService.getById(1);
        String expected = "Adel";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetById5WorksOk(){
        String actual = clientCrudService.getById(5);
        String expected = "Mariia";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetById11WorksOk(){
        String actual = clientCrudService.getById(idOfCreatedClientWeiWuxian);
        String expected = "Wei Wuxian";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetAllWorksOk(){
        List<Client> actual = clientCrudService.getAll();
        //migration add 10 clients
        int expected = 10;

        Assertions.assertEquals(expected, actual.size());
        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    public void testThatMethodDeleteWorksOk(){
        long clientIdToDelete = clientCrudService.getIdByName("Wei Wuxian");
        clientCrudService.delete(clientIdToDelete);

        Assertions.assertNull(clientCrudService.getById(clientIdToDelete));
    }
}
