import client.Client;
import client.ClientCrudService;
import database.Database;
import database.DatabaseInitService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import planet.Planet;
import planet.PlanetCrudService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlanetCrudServiceTests {
    private static Connection connection;
    private static PlanetCrudService planetCrudService;

    @BeforeAll
    public static void initDb() throws SQLException {
        DatabaseInitService databaseInitService = new DatabaseInitService();
        databaseInitService.initDb();
        connection = Database.getInstance().getConnection();
        planetCrudService = new PlanetCrudService(connection);

    }
    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void testThatMethodCreatePlanetFantasyWorksOk() throws SQLException {
        Planet newPlanet =new Planet();
        newPlanet.setId("FANTASY");
        newPlanet.setName("Fantasy");
        planetCrudService.create(newPlanet);

        Assertions.assertNotNull(planetCrudService.getById("FANTASY"));
    }

    @Test
    public void testThatMethodGetPlanetEarthByIdWorksOk() throws SQLException {
        String actual = planetCrudService.getById("EARTH");
        String expected = "Earth";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetPlanetNeptuneById5WorksOk() throws SQLException {
        String actual = planetCrudService.getById("NEPTUNE");
        String expected = "Neptune";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetPlanetFantasyByIdWorksOk() throws SQLException {
        String actual = planetCrudService.getById("FANTASY");
        String expected = "Fantasy";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testThatMethodGetAllWorksOk(){
        List<Planet> actual = planetCrudService.getAll();
        //8 planets in database(created by migration) + 1 new created "Fantasy"(by tests)
        int expected = 9;

        Assertions.assertEquals(expected, actual.size());
        Assertions.assertNotNull(actual);
        Assertions.assertFalse(actual.isEmpty());
    }

    @Test
    public void testThatMethodDeleteWorksOk() throws SQLException {
        String planetIdToDelete = "FANTASY";
        planetCrudService.delete(planetIdToDelete);

        Assertions.assertNull(planetCrudService.getById(planetIdToDelete));
    }
}
