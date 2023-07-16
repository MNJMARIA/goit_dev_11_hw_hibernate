package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientCrudService implements IClientCrudService{
    private PreparedStatement createSt;
    private PreparedStatement deleteSt;
    private PreparedStatement getByNameSt;
    private PreparedStatement listAllSt;

    public ClientCrudService(Connection connection) throws SQLException {
        createSt = connection
                .prepareStatement("INSERT INTO client (name) VALUES (?)");

        deleteSt = connection
                .prepareStatement("DELETE FROM client WHERE id = ?");

        getByNameSt = connection
                .prepareStatement("SELECT id FROM client WHERE name = ?");

        listAllSt = connection.prepareStatement("SELECT id, name FROM client");
    }

    @Override
    public long create(String name) throws SQLException {
        createSt.setString(1, name);
        createSt.executeUpdate();

        getByNameSt.setString(1, name);

        try (ResultSet rs = getByNameSt.executeQuery()) {
            if (!rs.next()) {
                return -1;
            }
            long id = rs.getLong("id");
            return id;
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        deleteSt.setLong(1, id);

        deleteSt.executeUpdate();
    }

    public List<Client> listAll(){
        try(ResultSet rs = listAllSt.executeQuery()) {
            List<Client> clients = new ArrayList<>();

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));

                clients.add(client);
            }

            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
