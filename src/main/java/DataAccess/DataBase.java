package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private Connection personConnection = null;
    private Connection eventConnection = null;
    private Connection userConnection = null;
    private Connection authConnection = null;

    /**
     *
     * @return
     * @throws DataAccessException
     */
    public Connection getPersonConnection() throws DataAccessException {
        if(this.personConnection == null) {
            openPersonConnection();
        }
        return personConnection;
    }

    /**
     *
     * @return
     * @throws DataAccessException
     */
    public Connection getEventConnection() throws DataAccessException {
        if(this.eventConnection == null) {
            openEventConnection();
        }
        return eventConnection;
    }

    /**
     *
     * @return
     * @throws DataAccessException
     */
    public Connection getUserConnection() throws DataAccessException {
        if(this.userConnection == null) {
            openUserConnection();
        }
        return userConnection;
    }

    /**
     *
     * @return
     * @throws DataAccessException
     */
    public Connection getAuthConnection() throws DataAccessException {
        if(this.authConnection == null) {
            openAuthConnection();
        }
        return authConnection;
    }

    /**
     *
     * @throws DataAccessException
     */
    public void openPersonConnection() throws DataAccessException {
        final String url = "jdbc:sqlite:persons.sqlite";
        if(this.personConnection != null) {
            return;
        }
        try {
            this.personConnection = DriverManager.getConnection(url);
            this.personConnection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to persons database");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public  void openEventConnection() throws DataAccessException {
        final String url = "jdbc:sqlite:events.sqlite";

        try {
            this.eventConnection = DriverManager.getConnection(url);
            this.eventConnection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to Events database");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void openUserConnection() throws DataAccessException {
        final String url = "jdbc:sqlite:user.sqlite";

        try {
            this.userConnection = DriverManager.getConnection(url);
            this.userConnection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to user database");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void openAuthConnection() throws DataAccessException {
        final String url = "jdbc:sqlite:authToken.sqlite";

        try {
            this.authConnection = DriverManager.getConnection(url);
            this.authConnection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to auth database");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void closeAllConnections() throws DataAccessException {
        closePersonConnection(false);
        closeAuthConnection(false);
        closeEventConnection(false);
        closeUserConnection(false);
    }

    /**
     *
     * @param commit
     * @throws DataAccessException
     */
    public void closePersonConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.personConnection.commit();
            } else {
                this.personConnection.rollback();
            }
            this.personConnection.close();
            this.personConnection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     *
     * @param commit
     * @throws DataAccessException
     */
    public void closeEventConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.eventConnection.commit();
            } else {
                this.eventConnection.rollback();
            }
            this.eventConnection.close();
            this.eventConnection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     *
     * @param commit
     * @throws DataAccessException
     */
    public void closeAuthConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.authConnection.commit();
            } else {
                this.authConnection.rollback();
            }
            this.authConnection.close();
            this.authConnection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     *
     * @param commit
     * @throws DataAccessException
     */
    public void closeUserConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                this.userConnection.commit();
            } else {
                this.userConnection.rollback();
            }
            this.userConnection.close();
            this.userConnection = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     * Attempts to close all database connections.
     * @throws DataAccessException
     */
    public void clearTables() throws DataAccessException {
       clearPersonsTable();
       clearAuthTables();
       clearEventsTables();
       clearUsersTables();
    }

    /**
     *
     * @throws DataAccessException
     */
    public void clearPersonsTable() throws DataAccessException {
        try (Statement stmt = this.personConnection.createStatement()){
            String sql = "DELETE FROM persons;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing persons tables");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void clearEventsTables() throws DataAccessException {
        try (Statement stmt = eventConnection.createStatement()){
            String sql = "DELETE FROM events;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing events tables");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void clearUsersTables() throws DataAccessException {
        try (Statement stmt = userConnection.createStatement()){
            String sql = "DELETE FROM users;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing user tables");
        }
    }

    /**
     *
     * @throws DataAccessException
     */
    public void clearAuthTables() throws DataAccessException {
        try (Statement stmt = authConnection.createStatement()){
            String sql = "DELETE FROM authToken;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing authToken tables");
        }
    }

    /**
     *
     * @throws SQLException
     */
    //When database is created, it goes through and creates its own pre filled database.
    public DataBase() throws SQLException {
        instantiateNewDB();
    }


    /**
     * generates a new Database
     */
    private void instantiateNewDB() {
        String sql = "DROP TABLE IF EXISTS persons;\n" +
                "CREATE TABLE IF NOT EXISTS persons (\n" +
                "    Person_ID text not null primary key,\n" +
                "    Username text not null,\n" +
                "    First_Name text not null,\n" +
                "    Last_Name text not null,\n" +
                "    Gender varchar(25) not null,\n" +
                "    foreign key(persons)Father_ID text,\n" +
                "    foreign key(persons)Mother_ID text,\n" +
                "    foreign key(persons)Spouse_ID text\n" +
                ");";

        try {
            openPersonConnection();
            Statement stmt = this.personConnection.createStatement();
            stmt.execute(sql);
            sql = "SELECT name FROM sqlite_master WHERE type='table' AND 'persons';";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        sql = "\n" +
                "drop table if exists events;\n" +
                "create table events (\n" +
                "    Event_ID text not null primary key,\n" +
                "    foreign key(persons) Username text not null,\n" +
                "    foreign key(persons) Person_ID text not null,\n" +
                "    Latitude integer not null,\n" +
                "    Longitude integer not null,\n" +
                "    Country text not null,\n" +
                "    City text not null,\n" +
                "    EventType text not null,\n" +
                "    Year integer not null\n" +
                ");";

        try{
            openEventConnection();
            Statement stmt = this.eventConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        sql = "\n" +
                "drop table if exists user;\n" +
                "create table user (\n" +
                "    Username text not null primary key,\n" +
                "    Password text not null\n" +
                ");";

        try{
            openUserConnection();
            Statement stmt = this.userConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        sql = "\n" +
                "drop table if exists authToken;\n" +
                "create table authToken (\n" +
                "    TokenID integer not null primary key,\n" +
                "    AuthToken text not null,\n" +
                "    foreign key(persons) Username text not null\n" +
                ");";

        try{
            openAuthConnection();
            Statement stmt = this.authConnection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
