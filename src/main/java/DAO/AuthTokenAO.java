package DAO;

import DataAccess.DataAccessException;
import DataAccess.DataBase;
import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenAO {

    public AuthToken getToken(Connection connection, String userID) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AuthToken at = null;

        try {
            String sql = "SELECT TokenID, authToken, Username FROM authToken WHERE Username = '"+ userID +"';";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            at = new AuthToken(rs.getString(1),
                    rs.getString(3), rs.getString(2));

        } finally {
            if(rs != null) {
                rs.close();
            }
            if(stmt != null) {
                stmt.close();
            }
        }

        return at;
    }

    public void addToken(Connection connection, AuthToken token) throws DataAccessException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO (TokenID, AuthToken, Username)  VALUES(?, ?, ?);";

            stmt = connection.prepareStatement(sql);

            stmt.setString(1, token.getTokenID());
            stmt.setString(2, token.getToken());
            stmt.setString(3, token.getUserName());
        } catch (SQLException e){
            throw new DataAccessException("Error inserting New Token\n");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteToken(Connection connection, String userName) {

    }

}
