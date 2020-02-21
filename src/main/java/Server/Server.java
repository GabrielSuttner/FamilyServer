package Server;

import DataAccess.DataBase;

import java.sql.SQLException;

public class Server {
    public static void main(String argv[]){

        try {
            DataBase db = new DataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
}
