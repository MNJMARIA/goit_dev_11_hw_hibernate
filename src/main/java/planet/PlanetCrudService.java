package planet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlanetCrudService implements IPlanetCrudService{
    private PreparedStatement createSt;
    private PreparedStatement deleteSt;

    public PlanetCrudService(Connection connection) throws SQLException {
        createSt = connection
                .prepareStatement("INSERT INTO planet (id, name) VALUES (?, ?)");

        deleteSt = connection
                .prepareStatement("DELETE FROM planet WHERE id = ?");
    }

    @Override
    public void create(Planet planet) throws SQLException {
        createSt.setString(1, planet.getId());
        createSt.setString(2, planet.getName());

        createSt.executeUpdate();
    }

    @Override
    public void delete(String id) throws SQLException {
        deleteSt.setString(1, id);

        deleteSt.executeUpdate();
    }
}
