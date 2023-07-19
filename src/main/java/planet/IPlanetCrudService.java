package planet;

import java.sql.SQLException;
import java.util.List;

public interface IPlanetCrudService {
    void create(Planet planet);
    String getById(String id);
    List<Planet> getAll();
    void delete(String id);
}
