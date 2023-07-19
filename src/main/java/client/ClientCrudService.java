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
    private PreparedStatement getIdByNameSt;
    private PreparedStatement listAllSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement getAllSt;

    public ClientCrudService(Connection connection) throws SQLException {
        createSt = connection
                .prepareStatement("INSERT INTO client (name) VALUES (?)");

        deleteSt = connection
                .prepareStatement("DELETE FROM client WHERE id = ?");

        getIdByNameSt = connection
                .prepareStatement("SELECT id FROM client WHERE name = ?");

        getAllSt = connection
                .prepareStatement("SELECT id, name FROM client");

        getByIdSt = connection
                .prepareStatement("SELECT id, name FROM client WHERE id = ?");
    }

    @Override
    public void create(String name) throws SQLException {
        createSt.setString(1, name);
        createSt.executeUpdate();
    }
    public long getIdByName(String name){
        try{
            getIdByNameSt.setString(1, name);

            try (ResultSet rs = getIdByNameSt.executeQuery()) {
                if (!rs.next()) {
                    return -1;
                }
                long id = rs.getLong("id");
                return id;
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getById(long id) {
        try{
        getByIdSt.setLong(1, id);

            try(ResultSet rs = getByIdSt.executeQuery() ) {
                if (!rs.next()) {
                    return null;
                }
                String name = rs.getString("name");
                return name;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> getAll() {
        try(ResultSet rs = getAllSt.executeQuery()){
            List<Client> clients = new ArrayList<>();

            while(rs.next()){
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

    @Override
    public void delete(long id) throws SQLException {
        deleteSt.setLong(1, id);

        deleteSt.executeUpdate();
    }
}
