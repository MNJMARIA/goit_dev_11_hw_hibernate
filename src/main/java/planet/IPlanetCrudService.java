package planet;

import java.sql.SQLException;
import java.util.List;

public interface IPlanetCrudService {
    void create(Planet planet) throws SQLException;
    String getById(String id) throws SQLException;
    List<Planet> getAll();
    void delete(String id) throws SQLException;
}
