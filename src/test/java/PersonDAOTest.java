//insert
//clear
//retreive
import DAO.PersonAO;
import DataAccess.DataAccessException;
import DataAccess.DataBase;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PersonDAOTest {
    private DataBase db;
    private Person person;

    @BeforeEach
    public void setUp() {
        try {
            this.db = new DataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.person = new Person( "Gsutt", "Gabe", "Suttner", "m");
    }

    public void tearDown() throws DataAccessException {
        this.db.openPersonConnection();
        this.db.clearPersonsTable();
        this.db.closePersonConnection(false);
    }


    @Test
    public void insertPerson() throws Exception {
        try {
            Connection con = this.db.getPersonConnection();
            PersonAO pd = new PersonAO();
            pd.addPerson(con, this.person);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        }
    }

    public void getPerson() {

    }

    /**
     * @throws SQLException
     */
    @Test
    public void clearPersons() throws SQLException {
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = db.getPersonConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            String sql = "delete from persons";
            stmt = connection.prepareStatement(sql);

            int count = stmt.executeUpdate();

            sql = "delete from sqlite_sequence where name = 'persons'";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();

            System.out.printf("Deleted %d persons\n", count);

        } finally {
            if(stmt != null) {
                stmt.close();
            }
        }
    }
}