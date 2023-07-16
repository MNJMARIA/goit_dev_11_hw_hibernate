package client;

import java.sql.SQLException;

public interface IClientCrudService {
    long create(String name) throws SQLException;
    void delete(long id) throws SQLException;
}
