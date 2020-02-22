package DAO;

import DataAccess.DataAccessException;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAO {
    public void addUser(Connection connection, User user) throws DataAccessException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO user (Username, Password) " +
                    "VALUES(?, ?);";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());

            stmt.executeUpdate();
            System.out.println("Added user: " + user.getUserName());
        } catch(SQLException e ){
            throw new DataAccessException("Failed to add user: " + user.getUserName());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new DataAccessException("Error closing Result Set or PreparedStatement");
            }
        }
    }


    public User getUser(Connection connection, String userID) throws DataAccessException {
        User newU = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "SELECT username, password FROM user " +
                    "WHERE username = '" + userID + "';";
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();

            user = new User(rs.getString(1), rs.getString(2));
        } catch(SQLException e ){
            throw new DataAccessException("Error finding User");
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


        return user;
    }

    public List<User> getUsers(Connection connection) throws DataAccessException {
        List<User> users = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT Username, Password FROM user";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            while(rs.next()) {

                String userName = rs.getString(1);
                String password = rs.getString(2);
                users.add(new User(userName, password));
            }
        } catch(SQLException e ){
            throw new DataAccessException("Error retrieving Users");
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
        return users;
    }

    public void updateUser(Connection connection, User user) {

    }

}
