package DAO;

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
            String sql = "delete from persons";
            stmt = connection.prepareStatement(sql);
            if (stmt.executeUpdate() == 1) {
                System.out.printf("Deleted %s.\n", personID);
            } else  {
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

            if(stmt.executeUpdate() == 1) {
                System.out.println("Updated Person: " +person.getFirstName());
            } else {
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
            String sql = "select Person_ID, Username, First_Name, Last_Name, Gender from Person";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {

                String personId = rs.getString(1);
                String userName = rs.getString(2);
                String fName = rs.getString(3);
                String lName = rs.getString(4);
                String gender = rs.getString(5);

                people.add(new Person(userName, fName, lName, gender));
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

    /**
     * @param connection
     * @throws SQLException
     */
    public void clearPersons(Connection connection) throws SQLException {
        PreparedStatement stmt = null;
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
