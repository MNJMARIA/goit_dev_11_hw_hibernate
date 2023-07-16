package database;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    public void initDb() {
        //path to connect from H2 web console:  jdbc:h2:C:\Users\armyl\IdeaProjects\goit_dev_10_hw\goit_dev_10_hw_database
        //Create the Flyway instance and point to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(DatabaseConstants.CONNECTION_URL, null, null)
                .locations("classpath:db/migration") // Вказуємо розташування міграційних скриптів
                .load();

        //Start the migration
        flyway.migrate();
    }
}