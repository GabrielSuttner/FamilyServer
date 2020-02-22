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
import java.util.ArrayList;
import java.util.List;


public class PersonDAOTest {
    private DataBase db;
    private Person person;
    private Person diffPers;


    @BeforeEach
    public void setUp() {
        try {
            this.db = new DataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.person = new Person( "Gsutt", "Gabe", "Suttner", "m");
        this.diffPers = new Person("cbush","cory", "bushman", "f" );

    }



    @Test
    public void insertPerson() throws Exception {
        PersonAO pd = null;
        try {
            Connection con = this.db.getPersonConnection();
            pd = new PersonAO();
            pd.addPerson(con, this.person);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        }
        List<Person> people = pd.getPersons(db.getPersonConnection());

        Assertions.assertEquals(1, people.size());
    }
    @Test
    public void insertPersonFail() throws Exception {
        PersonAO pd = null;
        try {
            Connection con = this.db.getPersonConnection();
            pd = new PersonAO();
            pd.addPerson(con, this.person);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        }
        List<Person> people = pd.getPersons(db.getPersonConnection());
        Person compare = null;
        for(Person p : people) {
            compare = p;
        }
        boolean failed = false;
        try {
            Connection con = this.db.getPersonConnection();
            pd = new PersonAO();
            pd.addPerson(con, this.person);
            db.closePersonConnection(false);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        } catch (SQLException e) {
            e.printStackTrace();
            failed = true;
        }
        people = pd.getPersons(db.getPersonConnection());
        db.closePersonConnection(false);

        //check to make sure that no duplicate person added.
        Assertions.assertTrue(failed);

    }

    @Test
    public void getPerson() throws DataAccessException {
        PersonAO pd = null;
        try {
            Connection con = this.db.getPersonConnection();
            pd = new PersonAO();
            pd.addPerson(con, this.person);
        } catch (DataAccessException | SQLException e) {
            db.closeAllConnections();
        }
        String personID = this.person.getPersonID();
        Person newPerson = null;
        try {
            newPerson = pd.getPerson(db.getPersonConnection(), personID);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(newPerson.getPersonID(), person.getPersonID());
    }

    @Test
    public void getPersonFail() throws DataAccessException {
        PersonAO pd = null;
        try {
            Connection con = this.db.getPersonConnection();
            pd = new PersonAO();
            pd.addPerson(con, this.person);
        } catch (DataAccessException | SQLException e) {
            db.closeAllConnections();
        }
        String personID = this.person.getPersonID();
        Person newPerson = null;

        boolean failed = false;
        try {
            newPerson = pd.getPerson(db.getPersonConnection(), this.diffPers.getPersonID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            failed = true;
        }
        Assertions.assertTrue(failed);
    }



    /**
     * @throws SQLException
     */
    @Test
    public void clearPersons() throws SQLException {
        PersonAO pa = new PersonAO();
        List<Person> people = null;
        try {
            pa.addPerson(db.getPersonConnection(), person);
            pa.addPerson(db.getPersonConnection(), diffPers);
            this.db.clearPersonsTable();
            people = pa.getPersons(db.getPersonConnection());
            this.db.closePersonConnection(false);


        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(people.size(), 0);

    }

   // @Test
    public void addDumbData() throws DataAccessException {
        try {
            db.fillDB();
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PersonAO pa = new PersonAO();
        try {
            List<Person> people = pa.getPersons(db.getPersonConnection());
            this.db.closePersonConnection(false);

            Assertions.assertTrue(people.size() == 0);

        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        db.closeAllConnections();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        this.db.openPersonConnection();
        this.db.clearPersonsTable();
        this.db.closePersonConnection(false);
    }
}