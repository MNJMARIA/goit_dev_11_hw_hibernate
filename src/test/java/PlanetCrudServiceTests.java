import database.DatabaseInitService;
import org.junit.jupiter.api.*;
import planet.IPlanetCrudService;
import planet.Planet;
import planet.PlanetCrudService;

import java.util.List;

public class PlanetCrudServiceTests {
    private IPlanetCrudService planetCrudService;

    @BeforeEach
    public void beforeEach(){
        new DatabaseInitService().initDb();
        planetCrudService = new PlanetCrudService();
    }

    @Test
    public void testCreatePlanetWorksOk(){
        Planet newPlanet = new Planet();
        newPlanet.setId("FANTASY");
        newPlanet.setName("Fantasy");

        Planet createdPlanet = planetCrudService.create(newPlanet);

        Assertions.assertNotNull(createdPlanet.getId());
        Assertions.assertEquals("Fantasy", createdPlanet.getName());

        // Clean up
        planetCrudService.delete(createdPlanet.getId());
    }

    @Test
    public void testGetPlanetEarthByIdWorksOk(){
        String existingPlanetId = "EARTH";

        Planet actualPlanet = planetCrudService.getById(existingPlanetId);

        //info about existing planet
        Planet expectedPlanet = new Planet();
        expectedPlanet.setId(existingPlanetId);
        expectedPlanet.setName("Earth");

        Assertions.assertNotNull(actualPlanet);
        Assertions.assertEquals(existingPlanetId, actualPlanet.getId());
        Assertions.assertEquals(expectedPlanet, actualPlanet);
    }

    @Test
    public void testGetPlanetNeptuneByIdWorksOk(){
        String existingPlanetId = "NEPTUNE";

        Planet actualPlanet = planetCrudService.getById(existingPlanetId);

        //info about existing planet
        Planet expectedPlanet = new Planet();
        expectedPlanet.setId(existingPlanetId);
        expectedPlanet.setName("Neptune");

        Assertions.assertNotNull(actualPlanet);
        Assertions.assertEquals(existingPlanetId, actualPlanet.getId());
        Assertions.assertEquals(expectedPlanet, actualPlanet);
    }

    @Test
    public void testGetPlanetFantasyByIdWorksOk(){
        String notExistingPlanetId = "FANTASY";

        Planet actualPlanet = planetCrudService.getById(notExistingPlanetId);

        Assertions.assertNull(actualPlanet);
    }

    @Test
    public void testGetAllPlanetsWorksOk(){
        List<Planet> planets = planetCrudService.getAll();

        Assertions.assertNotNull(planets);
        Assertions.assertFalse(planets.isEmpty());
        // Check if there are exactly 8 planets (created by migration)
        Assertions.assertEquals(8, planets.size());
    }
    @Test
    public void testGetPlanetIdByNameWorksOk(){
        String existingPlanetName = "Venus";

        String actualPlanetId = planetCrudService.getIdByName(existingPlanetName);
        String expectedPlanetId = "VENUS";

        //Assertions.assertNotNull(actualPlanetId);
        Assertions.assertEquals(expectedPlanetId, actualPlanetId);
    }
    @Test
    public void testUpdatePlanetWorksOk(){
        Planet planet = new Planet();
        planet.setId("PLANETX");
        planet.setName("PlanetX");
        String updatedName = "NewPlanetX";

        Planet createdPlanet = planetCrudService.create(planet);

        Planet updatedPlanet = planetCrudService.update(createdPlanet.getId(), updatedName);

        Assertions.assertNotNull(updatedPlanet);
        Assertions.assertEquals(updatedName, updatedPlanet.getName());

        planetCrudService.delete(updatedPlanet.getId());
    }
    @Test
    public void testDeletePlanetWorksOk(){
        Planet newPlanet = new Planet();
        newPlanet.setId("NEWID");
        newPlanet.setName("NewPlanet");

        Planet createdPlanet = planetCrudService.create(newPlanet);

        boolean isDeleted = planetCrudService.delete(createdPlanet.getId());

        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(planetCrudService.getById(createdPlanet.getId()));
    }
}
