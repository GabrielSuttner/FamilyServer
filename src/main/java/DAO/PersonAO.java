package DAO;

import DataAccess.DataAccessException;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonAO {

    /**
     *
     * @param connection
     * @param person
     * @throws SQLException
     */
    public void addPerson(Connection connection, Person person) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO persons (Person_ID, " +
                    "Username, First_Name, Last_Name, Gender," +
                    "Father_ID, Mother_ID, Spouse_ID) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            if(stmt.executeUpdate() == 1) {
                System.out.println("Added Person: " +person.getFirstName());
            } else {
                System.out.println("Failed to add Person: " + person.getPersonID());
            }

        } finally {
            if(stmt!= null) {
                stmt.close();
            }
        }
    }

    /**
     *
     * @param connection
     * @param personID
     * @throws SQLException
     */
    public void deletePerson(Connection connection, String personID) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE from persons WHERE Person_ID = '" + personID +"';";
            stmt = connection.prepareStatement(sql);
            try{
            stmt.executeUpdate();
                System.out.printf("Deleted %s.\n", personID);
            } catch (SQLException e) {
                System.out.printf("Failed to delete %s.\n", personID);
            }
        } finally {
            if(stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     *
     * @param connection
     * @param person
     * @throws SQLException
     */
    public void updatePerson(Connection connection, Person person) throws SQLException {
        PreparedStatement stmt = null;
        try{
            String sql = "Update person" +
                    "set Username = ?, First_Name = ?, Last_Name = ?," +
                    "Gender = ?, Father_ID = ?, Mother_ID = ?, Spouse_ID = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            try {
                stmt.executeUpdate();
                System.out.println("Updated Person: " + person.getFirstName());
            } catch (SQLException e){
                System.out.println("Failed to update Person: " + person.getPersonID());
            }

        } finally {
            if(stmt!= null) {
                stmt.close();
            }
        }
    }

    /**
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Person> getPersons(Connection connection) throws SQLException {
        List<Person> people = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID FROM persons";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {

                String personId = rs.getString(1);
                String userName = rs.getString(2);
                String fName = rs.getString(3);
                String lName = rs.getString(4);
                String gender = rs.getString(5);
                String dad = rs.getString(6);
                String mom = rs.getString(7);
                String spouse = rs.getString(8);

                people.add(new Person(personId, userName, fName, lName, gender, dad, mom, spouse));
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(stmt != null) {
                stmt.close();
            }
        }

        return people;
    }

    public Person getPerson(Connection connection, String personID) throws DataAccessException {
        Person person = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID " +
                    "FROM persons " +
                    "WHERE Person_ID = '" + personID + "';";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            String personId = rs.getString(1);
            String userName = rs.getString(2);
            String fName = rs.getString(3);
            String lName = rs.getString(4);
            String gender = rs.getString(5);
            String dad = rs.getString(6);
            String mom = rs.getString(7);
            String spouse = rs.getString(8);


            person = new Person(personId, userName, fName, lName, gender, dad, mom, spouse);
        } catch(SQLException e ){
            throw new DataAccessException("Error finding Person");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new DataAccessException("Error closing Result Set or PreparedStatement");
            }
        }

        return person;
    }
}
