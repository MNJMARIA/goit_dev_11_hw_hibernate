package planet;

import java.sql.SQLException;

public interface IPlanetCrudService {
    void create(Planet planet) throws SQLException;
    void delete(String id) throws SQLException;
}
