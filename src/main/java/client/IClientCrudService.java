package client;

import java.util.List;

public interface IClientCrudService {
    Client create(String name);
    Client getById(long id);
    Client update(long id, String name);
    long getIdByName(String name);
    List<Client> getAll();
    boolean delete(long id);
}
