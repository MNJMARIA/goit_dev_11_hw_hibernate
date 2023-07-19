package client;

import java.sql.SQLException;
import java.util.List;

public interface IClientCrudService {
    void create(String name) throws SQLException;
    String getById(long id);
    List<Client> getAll();
    void delete(long id) throws SQLException;
}
