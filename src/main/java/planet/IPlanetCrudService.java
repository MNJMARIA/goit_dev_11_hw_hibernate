package planet;

import client.Client;

import java.sql.SQLException;
import java.util.List;

public interface IPlanetCrudService {
    Planet create(Planet planet);
    Planet getById(String id);
    String getIdByName(String name);

    List<Planet> getAll();
    Planet update(String id, String name);

    boolean delete(String id);
}
