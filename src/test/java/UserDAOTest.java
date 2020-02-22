import DAO.PersonAO;
import DAO.UserAO;
import DataAccess.DataAccessException;
import DataAccess.DataBase;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//insert
//clear
//retreive
public class UserDAOTest {
    private DataBase db;
    private User User;
    private User diffUser;


    @BeforeEach
    public void setUp() {
        try {
            this.db = new DataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.User = new User("Gabriel", "pig");
        this.diffUser = new User("cory", "doll");
    }

    @Test
    public void insertUser() throws Exception {
        UserAO pd = null;
        try {
            Connection con = this.db.getUserConnection();
            pd = new UserAO();
            pd.addUser(con, this.User);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        }
        List<User> people = pd.getUsers(db.getUserConnection());
        User compare = null;
        for(User p : people) {
            compare = p;
        }
        db.closeUserConnection(false);
        Assertions.assertEquals(1, people.size());
    }

    @Test
    public void insertFail() throws DataAccessException {
        UserAO pd = null;
        boolean failed = false;
        try {
            Connection con = this.db.getUserConnection();
            pd = new UserAO();
            pd.addUser(con, this.User);
            pd.addUser(con, this.User);
        } catch (DataAccessException e) {
            db.closeAllConnections();
            e.printStackTrace();
            failed = true;
        }

        db.closeUserConnection(false);
        //check to make sure that no duplicate User added.
        Assertions.assertTrue(failed);
    }

    @Test
    public void getUser() throws DataAccessException {
        UserAO pd = null;
        try {
            Connection con = this.db.getUserConnection();
            pd = new UserAO();
            pd.addUser(con, this.User);
        } catch (DataAccessException e) {
            db.closeAllConnections();
        }
        String UserID = this.User.getUserName();
        User newUser = null;
        try {
            newUser = pd.getUser(db.getUserConnection(), UserID);
        }  finally {
            db.closeUserConnection(false);
        }

        Assertions.assertEquals(newUser.getUserName(), User.getUserName());
    }

    @Test
    public void getUserFail() throws DataAccessException {
        UserAO pd = null;
        try {
            Connection con = this.db.getUserConnection();
            pd = new UserAO();
            pd.addUser(con, this.User);
        } catch (DataAccessException  e) {
            db.closeUserConnection(false);
        }
        String personID = this.User.getUserName();
        User newPerson = null;

        boolean failed = false;
        try {
            newPerson = pd.getUser(db.getUserConnection(), this.diffUser.getUserName());
        } catch (DataAccessException e) {
            e.printStackTrace();
            failed = true;
        } finally {
            db.closeUserConnection(false);
        }
        Assertions.assertTrue(failed);
    }

    /**
     * @throws SQLException
     */
    @Test
    public void clearUser() throws SQLException {
        UserAO pa = new UserAO();
        try {
            pa.addUser(db.getUserConnection(), User);
            pa.addUser(db.getUserConnection(), diffUser);
            this.db.clearUsersTables();
            List<User> people = pa.getUsers(db.getUserConnection());
            this.db.closeUserConnection(false);

            Assertions.assertTrue(people.size() == 0);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    public void tearDown() throws DataAccessException {
        this.db.openUserConnection();
        this.db.clearUsersTables();
        this.db.closeUserConnection(false);
    }
}