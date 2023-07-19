package client;

import java.sql.SQLException;
import java.util.List;

public interface IClientCrudService {
    void create(String name);
    String getById(long id);
    List<Client> getAll();
    void delete(long id);
}
