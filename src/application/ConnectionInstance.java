package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInstance {
        private String server;
        private String username;
        private String password; //password ng db
        private String database;
        private String errorMessage;
        private Connection connection;
        public ConnectionInstance(String server, String database, String username, String password) {

            this.server = server;
            this.database = database;
            this.username = username;
            this.password = password;

            try {
                connection = DriverManager.getConnection(buildURL(server,database), username, password);
                System.out.println("You are now connected to: " + server + " and can access " + database);
            }
            catch(SQLException SE) {
                System.out.println("Cannot Access Database...");
                errorMessage = "Cannot Access Database.";
            }
        }

        private String buildURL (String server, String database) {
            return String.format("jdbc:sqlserver://%s;databaseName=%s;encryption=true;trustServerCertificate=true", server, database);
        }

        // returns connection
        public Connection getConnection() {
            return connection;
        }

        // closes connection
        public void closeConnection() throws SQLException {
            connection.close();
        }

        public String returnDatabaseError () {
            return errorMessage;
        }
    }


